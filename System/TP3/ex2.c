
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>


sem_t mutex;


/*************************************************************
** Afficher (20 fois) une ligne composée de 40 chiffres.
**************************************************************/
void* affichage (void* ptr_num)
{
    int num = *((int*) ptr_num);
    for(int i = 0; i < 20; i++)
    {
        sem_wait(&mutex); /* prologue */
            for(int j=0; j<20; j++) printf("%d",num);
            usleep(400); /* pour être sur d'avoir des problèmes */
            for(int j=0; j<20; j++) printf("%d",num);
            printf("\n");
            fflush(stdout);
        sem_post(&mutex); /* épilogue */
        //usleep(1);
    }
    /* préparer un résultat (un entier) */
    int* result = malloc(sizeof(int));
    *result = (num + 10);
    return result;
}


/*************************************************************
** Initialiser le sémaphore, créer les deux threads et
** attendre leurs terminaisons.
**************************************************************/
int main (void) {
    pthread_t fils0, fils1;
    int num0 = 0, num1 = 1;
    void *result0, *result1;

    sem_init(&mutex, 0, 1);

    if (pthread_create(&fils0, NULL, affichage, &num0)) {
        perror("pthread_create");
        exit(EXIT_FAILURE);
    }
    if (pthread_create(&fils1, NULL, affichage, &num1)) {
        perror("pthread_create");
        exit(EXIT_FAILURE);
    }

    if (pthread_join(fils0, &result0)) {
        perror("pthread_join");
      exit(EXIT_FAILURE);
    }

    if (pthread_join(fils1, &result1)) {
        perror("pthread_join");
        exit(EXIT_FAILURE);
    }

    printf("result 0 = %d\n", *((int*) result0));
    printf("result 1 = %d\n", *((int*) result1));
    return (EXIT_SUCCESS);
}

/*
1] L'exclusion mutuelle est bien nécessaire, sans elle les threads se succèdent une boucle après l'autre.
   C'est à dire qu'ils affiche 20 caractères similaires au lieu de 40, les lignes ne sont plus homogènes
   et sont composés de 20 0 et 20 1 car usleep relache l'utilisation du CPU par le thread actuel et permet
   à l'autre de s'executer.

2] La fonction usleep met en pause le programme, ici pour 1 microseconde. Sans celle ci a la fin de la boucle
   affichage le mutex est relaché et instantanément repris, le premier thread ne lache donc pas l'execution
   et la fonction va afficher un bloc de 0, et une fois finis lache l'execution au second thread. Non commenté,
   cette fonction relache l'utilisation du CPU par le thread actuel laissant l'autre prendre la relève et ainsi
   de suite.
 */
