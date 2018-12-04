#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdbool.h>
#include <unistd.h>
#include <semaphore.h>

volatile int theChar;
volatile enum {READ,WRITE} job = READ;

#define BUFFER_SIZE (10)
char buffer[BUFFER_SIZE];
int ptr_input = 0;
int ptr_output = 0;

sem_t NVide;
sem_t NPlein;

/*************************************************************
** Producteur: Lire l'entrée standard et, pour chaque
** caractère, donner le tour au consommateur.
**************************************************************/

void* read_stdin (void* argument) {
    do {
        sem_wait(&NVide);
        //while (job != READ) {     /* attendre mon tour */
            // noting to do
            //usleep(100);
        //}
        theChar = getchar();
        printf("ecris\n");
        buffer[ptr_input] = theChar;
        ptr_input++;

        ptr_input = ptr_input%BUFFER_SIZE;

        sem_post(&NPlein);
        //job = WRITE;              /* donner le tour */
    } while (theChar != EOF);     /* Ctrl-D sur une ligne vide */
    return NULL;
}


/*************************************************************
** Consommateur: Attendre son tour et, pour chaque caractère,
** l'afficher et donner le tour au producteur.
**************************************************************/

void* write_to_stdout (void* name) {
    while (true) {
        sem_wait(&NPlein);
        //while (job != WRITE) {         /* attendre */
            //cpt++;
            sleep(1);
        //}
        if (theChar == EOF)
          break;

        printf("--> car=%c\n", buffer[ptr_output]);
        ptr_output++;

        ptr_output = ptr_output%BUFFER_SIZE;
        sem_post(&NVide);
        //job = READ;                    /* donner le tour */
    }
    return NULL;
}

/*************************************************************
** Créer les threads et attendre leurs terminaisons.
**************************************************************/
int main (void)
{
    pthread_t read_thread, write_thread;

    sem_init(&NVide, 0, BUFFER_SIZE);
    sem_init(&NPlein, 0, 0);

    if (pthread_create(&read_thread, NULL, write_to_stdout, NULL)) {
        perror("pthread_create");
        exit(EXIT_FAILURE);
    }

    if (pthread_create(&write_thread, NULL, read_stdin, NULL)) {
        perror("pthread_create");
        exit(EXIT_FAILURE);
    }

    if (pthread_join(read_thread, NULL)) {
        perror("pthread_join");
        exit(EXIT_FAILURE);
    }

    if (pthread_join(write_thread, NULL)) {
        perror("pthread_join");
        exit(EXIT_FAILURE);
    }

    printf("Fin du pere\n");
    return (EXIT_SUCCESS);
}
