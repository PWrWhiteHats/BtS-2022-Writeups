#include <stdio.h>
#include <sys/ptrace.h>
#include <stdlib.h>
#include <string.h>
#define MAX 255

char secret[] = "@wWFQBx2i\\pm1p\\u6p[61wz]w\\v6Zv2ejwy";

void hehehe (void) __attribute__((constructor));

void hehehe (void)
{
	fprintf(stderr, "hehehehe\n");
	if (ptrace(PTRACE_TRACEME, 0, 1, 0) == -1) 
	{
        	fprintf(stderr, "good good first one achieved, hint by LiveOverflow -> https://www.youtube.com/watch?v=dQw4w9WgXcQ\n");
        	exit(0);
    	}
}

char* xor(char* b)	
{	
	char *result = (char*) malloc(strlen(b) * sizeof(char));
	int k = 0;
	int i = 0;
	char key[] = "\x02\x03\x04\x05\x05\x04\x03\x02";
	int len = strlen(key);
	for(i=0; i<strlen(b); i++)
	{
		char tmp = b[i]^key[k%len];
		result[i] = tmp;
		k = k+1;
	}
	return result;
}

int main()
{
	char password[MAX];
    	printf(">");
    	scanf("%s",password);
    	if(!strncmp(password,xor("fljqgvvvgekwfangrow"),19))
    	{
    		fprintf(stderr, "%s\n",xor(secret));
    	}	
return 0;
}
