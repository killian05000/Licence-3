#include <stdio.h>
#include "syntabs.h"
#include "util.h"
#include "tabsymboles.h"
#include "parcours_arbre_abstrait.h"

void parcours_n_prog(n_prog *n);
void parcours_l_instr(n_l_instr *n);
void parcours_instr(n_instr *n);
void parcours_instr_si(n_instr *n);
void parcours_instr_tantque(n_instr *n);
void parcours_instr_affect(n_instr *n);
void parcours_instr_appel(n_instr *n);
void parcours_instr_retour(n_instr *n);
void parcours_instr_ecrire(n_instr *n);
void parcours_instr_pour(n_instr *n); //POUR
void parcours_l_exp(n_l_exp *n);
void parcours_exp(n_exp *n);
void parcours_varExp(n_exp *n);
void parcours_opExp(n_exp *n);
void parcours_intExp(n_exp *n);
void parcours_lireExp(n_exp *n);
void parcours_appelExp(n_exp *n);
int parcours_l_dec(n_l_dec *n);
void parcours_dec(n_dec *n);
void parcours_foncDec(n_dec *n);
void parcours_varDec(n_dec *n);
void parcours_tabDec(n_dec *n);
void parcours_var(n_var *n);
void parcours_var_simple(n_var *n);
void parcours_var_indicee(n_var *n);
void parcours_appel(n_appel *n);

int trace_abs2 = 1;


/*-------------------------------------------------------------------------*/

void parcours_n_prog(n_prog *n)
{
  portee = P_VARIABLE_GLOBALE;
  adresseLocaleCourante = 0;
  adresseArgumentCourant = 0;
  adresseGlobaleCourante = 0;
  parcours_l_dec(n->variables);
  parcours_l_dec(n->fonctions);
  //afficheTabsymboles();
  if(rechercheExecutable("main") == -1)
    erreur("pas de main");
}

/*-------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------*/

void parcours_l_instr(n_l_instr *n)
{
  if(n)
  {
  parcours_instr(n->tete);
  parcours_l_instr(n->queue);
  }
}

/*-------------------------------------------------------------------------*/

void parcours_instr(n_instr *n)
{
  if(n){
    if(n->type == blocInst) parcours_l_instr(n->u.liste);
    else if(n->type == affecteInst) parcours_instr_affect(n);
    else if(n->type == siInst) parcours_instr_si(n);
    else if(n->type == tantqueInst) parcours_instr_tantque(n);
    else if(n->type == appelInst) parcours_instr_appel(n);
    else if(n->type == retourInst) parcours_instr_retour(n);
    else if(n->type == ecrireInst) parcours_instr_ecrire(n);
  }
}

/*-------------------------------------------------------------------------*/

void parcours_instr_si(n_instr *n)
{
  parcours_exp(n->u.si_.test);
  parcours_instr(n->u.si_.alors);
  if(n->u.si_.sinon)
  {
    parcours_instr(n->u.si_.sinon);
  }
}

/*-------------------------------------------------------------------------*/

void parcours_instr_tantque(n_instr *n)
{
  parcours_exp(n->u.tantque_.test);
  parcours_instr(n->u.tantque_.faire);
}

/*-------------------------------------------------------------------------*/

void parcours_instr_affect(n_instr *n)
{
  parcours_var(n->u.affecte_.var);
  parcours_exp(n->u.affecte_.exp);
}

/*-------------------------------------------------------------------------*/

void parcours_instr_appel(n_instr *n)
{
  parcours_appel(n->u.appel);
}

/*-------------------------------------------------------------------------*/

void parcours_appel(n_appel *n)
{
  if(rechercheExecutable(n->fonction) != -1)
  {
    if(n->args == NULL && tabsymboles.tab[rechercheExecutable(n->fonction)].complement == 0)
      parcours_l_exp(n->args);
    else if ( tabsymboles.tab[rechercheExecutable(n->fonction)].complement == parcours_n_l_exp(n))
      parcours_l_exp(n->args);
    /*else
      erreur("Fonction lance avec un nombre d'arguments incorrect");*/
  }
  //else
    //erreur("fonction non declare");
}

/*-------------------------------------------------------------------------*/

void parcours_instr_retour(n_instr *n)
{
  parcours_exp(n->u.retour_.expression);
}

/*-------------------------------------------------------------------------*/

void parcours_instr_ecrire(n_instr *n)
{
  parcours_exp(n->u.ecrire_.expression);
}

/*-------------------------------------------------------------------------*/

void parcours_instr_pour(n_instr *n)
{
  parcours_instr(n->u.pour_.affect);
  parcours_exp(n->u.pour_.exp);
  parcours_instr(n->u.pour_.affect2);
  parcours_instr(n->u.pour_.faire);
}

/*-------------------------------------------------------------------------*/

void parcours_l_exp(n_l_exp *n)
{
  if(n){
    parcours_exp(n->tete);
    parcours_l_exp(n->queue);
  }
}

/*-------------------------------------------------------------------------*/

void parcours_exp(n_exp *n)
{
  if(n->type == varExp) parcours_varExp(n);
  else if(n->type == opExp) parcours_opExp(n);
  else if(n->type == intExp) parcours_intExp(n);
  else if(n->type == appelExp) parcours_appelExp(n);
  else if(n->type == lireExp) parcours_lireExp(n);
}

/*-------------------------------------------------------------------------*/

void parcours_varExp(n_exp *n)
{
  parcours_var(n->u.var);
}

/*-------------------------------------------------------------------------*/

void parcours_opExp(n_exp *n)
{
  if( n->u.opExp_.op1 != NULL ) {
    parcours_exp(n->u.opExp_.op1);
  }
  if( n->u.opExp_.op2 != NULL ) {
    parcours_exp(n->u.opExp_.op2);
  }
}

/*-------------------------------------------------------------------------*/

void parcours_intExp(n_exp *n)
{
  char texte[ 50 ]; // Max. 50 chiffres
  sprintf(texte, "%d", n->u.entier);
}

/*-------------------------------------------------------------------------*/
void parcours_lireExp(n_exp *n)
{

}

/*-------------------------------------------------------------------------*/

void parcours_appelExp(n_exp *n)
{
  parcours_appel(n->u.appel);
}

/*-------------------------------------------------------------------------*/

int parcours_l_dec(n_l_dec *n)
{
  if(n)
  {
    parcours_dec(n->tete);
    int nbArgs = parcours_l_dec(n->queue);
    return nbArgs+1;
  }
  return 0;
}

/*-------------------------------------------------------------------------*/

int parcours_n_l_exp(n_l_exp *n)
{
  if(n)
  {
    parcours_exp(n->tete);
    int nbArgs = parcours_n_l_exp(n->queue);
    return nbArgs+1;
  }
  return 0;
}

/*-------------------------------------------------------------------------*/

void parcours_dec(n_dec *n)
{
  if(rechercheDeclarative(n->nom) != -1)
  {
    if(n->type==foncDec)
      erreur("fonction deja declaré localement");
    else if(n->type==varDec)
      erreur("variable deja declare localement");
    else if(n->type==tabDec)
      erreur("tableau deja declare localement");
  }

  if(rechercheExecutable(n->nom) != -1)
  {
    if(n->type==foncDec)
      erreur("fonction deja declaré globalement");
    else if(n->type==varDec)
      erreur("variable deja declare globalement");
    else if(n->type==tabDec)
      erreur("tableau deja declare globalement");
  }

  if(n)
  {
    if(n->type == foncDec)
    {
      parcours_foncDec(n);
    }
    else if(n->type == varDec)
    {
      parcours_varDec(n);
    }
    else if(n->type == tabDec)
    {
      parcours_tabDec(n);
    }
  }
}

/*-------------------------------------------------------------------------*/

void parcours_foncDec(n_dec *n)
{
  int lignF = ajouteIdentificateur(n->nom, portee, T_FONCTION, 0, 0);

  entreeFonction();
  int nbArgs = parcours_l_dec( n->u.foncDec_.param);
  tabsymboles.tab[lignF].complement = nbArgs;
  portee = P_VARIABLE_LOCALE;

  parcours_l_dec(n->u.foncDec_.variables);
  parcours_instr(n->u.foncDec_.corps);
  sortieFonction(trace_abs2);
}

/*-------------------------------------------------------------------------*/

void parcours_varDec(n_dec *n)
{
  if (portee == P_ARGUMENT)
  {
    ajouteIdentificateur(n->nom, portee, T_ENTIER, adresseArgumentCourant, 1);
    adresseArgumentCourant+=4;
  }
  else if (portee == P_VARIABLE_LOCALE)
  {
    ajouteIdentificateur(n->nom, portee, T_ENTIER, adresseLocaleCourante, 1);
    adresseLocaleCourante+=4;
  }
  else if (portee == P_VARIABLE_GLOBALE)
  {
    ajouteIdentificateur(n->nom, portee, T_ENTIER, adresseGlobaleCourante, 1);
    adresseGlobaleCourante+=4;
  }
}

/*-------------------------------------------------------------------------*/

void parcours_tabDec(n_dec *n)
{
  if(portee == P_VARIABLE_GLOBALE)
  {
    ajouteIdentificateur(n->nom, portee, T_TABLEAU_ENTIER, adresseGlobaleCourante, n->u.tabDec_.taille);
    adresseGlobaleCourante+=(4*n->u.tabDec_.taille);
  }
  else
    erreur("Un tableau ne peut être déclaré que comme une variable globale");

  //char texte[100]; // Max. 100 chars nom tab + taille
  //sprintf(texte, "%s[%d]", n->nom, n->u.tabDec_.taille);
}

/*-------------------------------------------------------------------------*/

void parcours_var(n_var *n)
{
  if(rechercheExecutable(n->nom)!=-1)
  {
    if(n->type == simple)
    {
      if(tabsymboles.tab[rechercheExecutable(n->nom)].type != T_ENTIER)
        erreur("Usage incorrect de la variable [erreur de typage]");
      else
        parcours_var_simple(n);

    }else if(n->type == indicee)
    {
      if(tabsymboles.tab[rechercheExecutable(n->nom)].type != T_TABLEAU_ENTIER)
        erreur("Usage incorrect de la variable tableau [erreur de typage]");
      else
        parcours_var_indicee(n);
    }
    else
    {
      erreur("type inconnu");
    }
  }
  /*else
    erreur("Variable non déclarer");
  if(n->type == simple) {
    ajouteIdentificateur(n->name, portee, type, adresse, complement);
    parcours_var_simple(n);
  }
  else if(n->type == indicee) {
    ajouteIdentificateur(n->name, portee, type, adresse, complement);
    parcours_var_indicee(n);
  }*/
}

/*-------------------------------------------------------------------------*/

void parcours_var_simple(n_var *n)
{
  int nbLigne = rechercheExecutable(n->nom);

  if(tabsymboles.tab[nbLigne].type != T_ENTIER)
    erreur("La variable n'est pas un entier");
}

/*-------------------------------------------------------------------------*/

void parcours_var_indicee(n_var *n)
{
  parcours_exp( n->u.indicee_.indice );

  int nbLigne = rechercheExecutable(n->nom);

  if(tabsymboles.tab[nbLigne].type != T_TABLEAU_ENTIER)
    erreur("La variable n'est pas un tableau");
}

/*-------------------------------------------------------------------------*/
