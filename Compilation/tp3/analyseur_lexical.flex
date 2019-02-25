/*
 * Analyseur lexical du compilateur L en FLEX
 */
%{
/* code copié AU DÉBUT de l'analyseur */

#include "analyseur_syntaxique.tab.h"
%}
%option yylineno
%option nounput
%option noinput

/* Déclarations à compléter ... */

lettre                 [a-zA-Z]|"$"|"_"
chiffre                [0-9]
nombre                 [0-9][0-9]*
alphanum               {lettre}|{chiffre}

%%

";"                    {return POINT_VIRGULE;}
"+"                    {return PLUS;}
"-"                    {return MOINS;}
"*"                    {return FOIS;}
"/"                    {return DIVISE;}
"("                    {return PARENTHESE_OUVRANTE;}
")"                    {return PARENTHESE_FERMANTE;}
"["                    {return CROCHET_OUVRANT;}
"]"                    {return CROCHET_FERMANT;}
"{"                    {return ACCOLADE_OUVRANTE;}
"}"                    {return ACCOLADE_FERMANTE;}
"="                    {return EGAL;}
"<"                    {return INFERIEUR;}
"&"                    {return ET;}
"|"                    {return OU;}
"!"                    {return NON;}
","                    {return VIRGULE;}
"si"                   {return SI;}
"alors"                {return ALORS;}
"sinon"                {return SINON;}
"tantque"              {return TANTQUE;}
"faire"                {return FAIRE;}
"entier"               {return ENTIER;}
"retour"               {return RETOUR;}
"lire"                 {return LIRE;}
"ecrire"               {return ECRIRE;}
{nombre}               {yylval.ivalue = atoi(yytext); return NOMBRE;}
{lettre}{alphanum}*    {yylval = duplique_chaine(yytext); return IDENTIF;}
#.*                    {}
\n                     {}
\t                     {}
" "+                   {}

%%

/* Code copié À LA FIN de l'analyseur */

int yywrap(){
  return 1;
}

/***********************************************************************
 * Fonction auxiliaire appelée par l'analyseur syntaxique pour
 * afficher des messages d'erreur et l'arbre XML
 **********************************************************************/

char *tableMotsClefs[] = {"si", "alors", "sinon", "tantque", "faire", "entier", "retour", "lire", "ecrire"};
int codeMotClefs[] = {SI, ALORS, SINON, TANTQUE, FAIRE, ENTIER, RETOUR, LIRE, ECRIRE};
int nbMotsClefs = 9;

void nom_token( int token, char *nom, char *valeur ) {
  int i;
  strcpy( nom, "symbole" );
  if(token == POINT_VIRGULE) strcpy( valeur, "POINT_VIRGULE");
  else if(token == PLUS) strcpy(valeur, "PLUS");
  else if(token == MOINS) strcpy(valeur, "MOINS");
  else if(token == FOIS) strcpy(valeur, "FOIS");
  else if(token == DIVISE) strcpy(valeur, "DIVISE");
  else if(token == PARENTHESE_OUVRANTE) strcpy(valeur, "PARENTHESE_OUVRANTE");
  else if(token == PARENTHESE_FERMANTE) strcpy(valeur, "PARENTHESE_FERMANTE");
  else if(token == CROCHET_OUVRANT) strcpy(valeur, "CROCHET_OUVRANT");
  else if(token == CROCHET_FERMANT) strcpy(valeur, "CROCHET_FERMANT");
  else if(token == ACCOLADE_OUVRANTE) strcpy(valeur, "ACCOLADE_OUVRANTE");
  else if(token == ACCOLADE_FERMANTE) strcpy(valeur, "ACCOLADE_FERMANTE");
  else if(token == EGAL) strcpy(valeur, "EGAL");
  else if(token == INFERIEUR) strcpy(valeur, "INFERIEUR");
  else if(token == ET) strcpy(valeur, "ET");
  else if(token == OU) strcpy(valeur, "OU");
  else if(token == NON) strcpy(valeur, "NON");
  else if(token == VIRGULE) strcpy(valeur, "VIRGULE");
  else if( token == IDENTIF ) {
    strcpy( nom, "identificateur" );
    strcpy( valeur, yytext );
  }
  else if( token == NOMBRE ) {
    strcpy( nom, "nombre" );
    strcpy( valeur, yytext );
  }
  else {
    strcpy(nom, "mot_clef");
    for(i = 0; i < nbMotsClefs; i++){
      if( token ==  codeMotClefs[i] ){
        strcpy( valeur, tableMotsClefs[i] );
        break;
      }
    }
  }
}
