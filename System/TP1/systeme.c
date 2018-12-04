
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "cpu.h"
#include "asm.h"
#include "systeme.h"

enum {
  SYSC_EXIT,
  SYSC_PUTI,
  SYSC_NEW_THREAD
};

#define MAX_PROCESS  (20)   /* nb maximum de processus  */

#define EMPTY         (0)   /* processus non-prêt       */
#define READY         (1)   /* processus prêt           */

struct {
    PSW  cpu;               /* mot d'état du processeur */
    int  state;             /* état du processus        */
    }
    process[MAX_PROCESS];   /* table des processus      */

int current_process = -1;   /* nu du processus courant  */

/**********************************************************
** Démarrage du système (création d'un programme)
***********************************************************/

PSW system_init(void) {
    PSW cpu;

    printf("Booting\n");
    /*** création d'un programme ***/

    // begin(20);
    //         set(R1, 0);
    //         set(R2, 0);
    //     label(10);
    //         add(R1, R2, 5);
    //         sysc(R1,0,SYSC_PUTI);
    //         load(R0, R1, 0);
    //         nop();
    //         jump(10);
    //     // sysc(0, 0, SYSC_EXIT);
    // end();

// begin(20);
//     /*** créer un thread ***/
//     sysc(R1, R1, SYSC_NEW_THREAD);  /* créer un thread  */
//     if_gt(10);                      /* le père va en 10 */
// 
//     /*** code du fils ***/
//     set(R3, 1000);                  /* R3 = 1000    */
//     sysc(R3, 0, SYSC_PUTI);         /* afficher R3  */
//     nop();
//     nop();
// 
//     /*** code du père ***/
//     label(10);                     /* set label 10   */
//     set(R3, 2000);                 /* R3 = 1000      */
//     sysc(R3, 0, SYSC_PUTI);        /* afficher R3    */
//     sysc(0, 0, SYSC_EXIT);         /* fin du thread  */
// end();


    begin(20);
        set(R0, 0);
        store(R0, R0, 0);
        
        sysc(R2, R2, SYSC_NEW_THREAD);
        if_gt(10);

        // fils
        set(R1, 0);
        set(R2, 1);
        label(20);
            add(R1, R2, 0);
            store(R1, R0, 0);
            jump(20);
        
        // père
        label(10);
            load(R1, R0, 0);
            sysc(R1, 0, SYSC_PUTI);
            jump(10);
        end();

    /*** valeur initiale du PSW ***/
    memset (&cpu, 0, sizeof(cpu));
    cpu.PC = 0;
    cpu.SB = 20;
    cpu.SS = 40;

    for (int i = 0; i < MAX_PROCESS; i++) {
      process[i].state = EMPTY;
    }

    process[0].cpu = cpu;
    process[0].state = READY;

    // process[1].cpu = cpu;
    // process[1].state = READY;

    return cpu;
}

int new_thread(PSW cpu) {
  int child = 0;
  while(process[child].state != EMPTY) {
    ++child;
  }

  cpu.AC = 0;
  process[child].cpu = cpu;
  process[child].state = READY;

  return child;
}

PSW sysc_thread(PSW cpu) {
  cpu.AC = cpu.DR[cpu.RI.i] = new_thread(cpu);
  return cpu;
}

void sysc_exit()
{
  process[current_process].state = EMPTY;
  for (int i = 0 ; i < MAX_PROCESS; ++i) {
    if (process[i].state != EMPTY) {
      return;
    }
  }
  printf("sysc_exit \n");
  exit(0);
}

void sysc_puti(PSW m)
{
  printf("Entier stocké dans le premier registre %d \n", m.DR[m.RI.i]);
}

PSW scheduler(PSW m)
{
   /* sauvegarder le processus courant si il existe */
   process[current_process].cpu = m;
  do
  {
      current_process = (current_process + 1) % MAX_PROCESS;
  } while (process[current_process].state != READY);
  /* relancer ce processus */
  return process[current_process].cpu;
}



/**********************************************************
** Traitement des interruptions par le système (mode système)
***********************************************************/

PSW process_interrupt(PSW m) {
    //dump_cpu(m);
    switch (m.IN) {
        case INT_SEGV:
            printf(" : INT_SEGV (violation mémoire)\n");
            exit(1);
            break;
        case INT_TRACE:
            //printf("INT_TRACE (trace entre chaque instruction)\n");
            //dump_cpu(m);
            m = scheduler(m);
            break;
        case INT_INST:
            printf("INT_INST (instruction inconnue)\n");
            exit(2);
            break;
        case INT_SYSC:
            printf("INT_SYSC (appel au systeme)\n");
            switch (m.RI.ARG)
            {
              case SYSC_EXIT:
                sysc_exit();
                m= scheduler(m);
                break;
              case SYSC_PUTI:
                sysc_puti(m);
                break;
               case SYSC_NEW_THREAD:
               m= sysc_thread(m);
               break;
            }
            break;
        case INT_KEYBOARD:
            printf("INT_KEYBOARD (événement clavier (simulé))\n");
            break;
        default:
            break;
    }
    return m;
}
