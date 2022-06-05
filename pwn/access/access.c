#include <stdio.h>
#include <string.h>
#include<stdlib.h>
#define SIZE 2048

int main(void)
{
    setbuf(stdout, NULL);
    setbuf(stderr, NULL);
    char *faker3="Entered";
    char *faker4=" password: ";
    char buff[100];
    char username[100];
    char read_el[SIZE];
    FILE *fp=fopen("pass.txt", "r");
    if(fp == NULL){
        exit(1);
    }

    fscanf(fp, "%[^\n]", read_el);
    fclose(fp);

    printf("WELCOME TO AFINE COMPANY LOGIN PAGE\nUsername: ");
    scanf("%s",username);
    printf("Password: ");
    scanf("%s",buff);
    if(strcmp(buff, read_el))
    {
        printf("%s",faker3);
        printf("%s",faker4);
        printf(buff);
        printf(" - IS WRONG.");
    }
    else
    {
        printf ("BtSCTF{%s}", read_el);
    }
    return 0;
}
