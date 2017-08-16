// $Id: spellchk.c,v 1.9 2014-05-15 21:07:47-07 - - $

#include <errno.h>
#include <libgen.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <ctype.h>
#include "debug.h"
#include "hashset.h"
#include "yyextern.h"

#define STDIN_NAME       "-"
#define DEFAULT_DICTNAME \
        "/afs/cats.ucsc.edu/courses/cmps012b-wm/usr/dict/words"
#define DEFAULT_DICT_POS 0
#define EXTRA_DICT_POS   1
#define NUMBER_DICTS     2

int x_count = 0;

void print_error (const char *object, const char *message) {
   fflush (NULL);
   fprintf (stderr, "%s: %s: %s\n", program_name, object, message);
   fflush (NULL);
   exit_status = EXIT_FAILURE;
}

FILE *open_infile (const char *filename) {
   FILE *file = fopen (filename, "r");
   if (file == NULL){
       print_error (filename, strerror (errno));
       exit_status = 2;
   }
   DEBUGF ('m', "filename = \"%s\", file = 0x%p\n", filename, file);
   return file;
}

void spellcheck (const char *filename, hashset *hashset) {
   yylineno = 1;
   DEBUGF ('m', "filename = \"%s\", hashset = 0x%p\n",
             filename, hashset);

   for (;;) {
      int token = yylex ();
      if (token == 0) break;
     // printf("in spellcheck method\n");
      char* word = yytext;
      if(!has_hashset(hashset,word)){
// printf("in loop to test without modifying capitalization.\n");
         char* lowercase = strdup(word);
         for(int i = 0; lowercase[i] != '\0'; i++ ){           
            lowercase[i] = tolower(lowercase[i]);
     //       printf("lowercase version: %s\n", lowercase);
         if(!(has_hashset(hashset,lowercase))){
     //       printf("checking lowercase / not found in dict\n");
            printf("-: %d: %s\n",yylineno,word);
            exit_status = 1;
         }
         free(lowercase);
      }   
DEBUGF ('m', "line %d, yytext = \"%s\"\n", yylineno, yytext);
//printf("%s: %d: %s\n", filename, yylineno, yytext);
   }
}
}
void load_dictionary (const char *dictionary_name, hashset *hashset) {
   if (dictionary_name == NULL) return;
   DEBUGF ('m', "dictionary_name = \"%s\", hashset = %p\n",
           dictionary_name, hashset);
   char buffer[1024];
   FILE *fp = open_infile(dictionary_name);
   if(fp != NULL){
      for(;;) {
         char* line = fgets(buffer, sizeof buffer, fp);
     // print_error(dictionary_name, "No such file or directory");
         if(line == NULL) break;
         char* nlpos = strchr(buffer,'\n');
         if(nlpos != NULL){
            *nlpos = '\0';
         }
      put_hashset(hashset,line);
      }
   }
   fclose(fp);
   //STUBPRINTF ("Open dictionary, load it, close it\n");
}


void scan_options (int argc, char** argv,
                   char **default_dictionary,
                   char **user_dictionary) {
   // Scan the arguments and set flags.
   opterr = false;
   for (;;) {
      int option = getopt (argc, argv, "nxyd:@:");
      if (option == EOF) break;
      switch (option) {
         char optopt_string[16]; // used in default:
         case 'd': *user_dictionary = optarg;
                   break;
         case 'n': *default_dictionary = NULL;
                   break;
         case 'x': x_count++;
                   break;
         case 'y': yy_flex_debug = true;
                   break;
         case '@': set_debug_flags (optarg);
                   if (strpbrk (optarg, "@y")) yy_flex_debug = true;
                   break;
         default : sprintf (optopt_string, "-%c", optopt);
                   print_error (optopt_string, "invalid option");
                   break;
      }
   }
}


int main (int argc, char **argv) {
   program_name = basename (argv[0]);
   char *default_dictionary = DEFAULT_DICTNAME;
   char *user_dictionary = NULL;
   hashset *hashset = new_hashset ();
   yy_flex_debug = false;
   scan_options (argc, argv, &default_dictionary, &user_dictionary);
   if(default_dictionary == NULL && user_dictionary == NULL)
      print_error(default_dictionary, "no dicionaries present");
   // Load the dictionaries into the hash table.
   load_dictionary (default_dictionary, hashset);
   load_dictionary (user_dictionary, hashset);

   // Read and do spell checking on each of the files.
   if (optind >= argc) {
      yyin = stdin;
      spellcheck (STDIN_NAME, hashset);
   }else {
      for (int fileix = optind; fileix < argc; ++fileix) {
         DEBUGF ('m', "argv[%d] = \"%s\"\n", fileix, argv[fileix]);
         char *filename = argv[fileix];
         if (strcmp (filename, STDIN_NAME) == 0) {
            yyin = stdin;
            spellcheck (STDIN_NAME, hashset);
         }else {
            yyin = open_infile (filename);
            if (yyin == NULL) continue;
            spellcheck (filename, hashset);
            fclose (yyin);
         }
      }
   }
   if(x_count > 0)
      print_hash(hashset);
   if(x_count > 1)
      dump_hash(hashset);
   free_hashset(hashset);
   yylex_destroy ();
   return exit_status;
}

