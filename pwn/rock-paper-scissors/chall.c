#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <string.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
#define ERR(s)              \
	{                       \
		puts((s));        \
		exit(EXIT_FAILURE); \
	}
void flag();
void win(char);
void lose(char);
void draw(char);

void (*options[3])(char) = {win, lose, draw};

void init()
{
	setbuf(stdout, NULL);
	setbuf(stdout, NULL);
	setbuf(stderr, NULL);
	srand(time(0));
	alarm(120);
}

void start_game(char *argv[])
{
	char player_choice='\0';
	char buffer[16]= {0};

	puts("Rock (r), Paper (p) or Scissors (s)?");
	player_choice = getchar();

	options[rand() % 3](player_choice);

	puts("Would you like to play again? (yes/no)");
	read(0, buffer, 48);

	if (strncmp(buffer, "yes",3) == 0)
		execv(argv[0], argv);

}

int main(int argc, char *argv[])
{
	init();
	start_game(argv);
}

void win(char c)
{
	switch (c)
	{
	case 'r':
		printf("Computer's choice: ");
		puts("Scissors");
		break;
	case 's':
		printf("Computer's choice: ");
		puts("Paper");
		break;
	case 'p':
		printf("Computer's choice: ");
		puts("Rock");
		break;
	default:
		puts("bad input");
		return;
	}
	puts("You win!");
	unsigned long *x = __builtin_frame_address(0) - 8;
	printf("Your reward: 0x%lX\n", *x);
}
void lose(char c)
{
	switch (c)
	{
	case 'r':
		printf("Computer's choice: ");
		puts("Paper");
		break;
	case 's':
		printf("Computer's choice: ");
		puts("Rock");
		break;
	case 'p':
		printf("Computer's choice: ");
		puts("Scissors");
		break;
	default:
		puts("bad input");
		return;
	}
	printf("You lose!\n");
}
void draw(char c)
{
	switch (c)
	{
	case 'r':
		printf("Computer's choice: ");
		puts("Rock");
		break;
	case 's':
		printf("Computer's choice: ");
		puts("Scissors");
		break;
	case 'p':
		printf("Computer's choice: ");
		puts("Paper");
		break;
	default:
		puts("bad input");
		return;
	}
	printf("%c\n", c);
	printf("Draw!\n");
}

void flag()
{
	int flag_fd = open("./flag.txt", O_RDONLY);
	if (flag_fd == -1)
		ERR("flag read error");
	struct stat st;
	fstat(flag_fd, &st);
	char *flag_buf = malloc(st.st_size + 1);
	if (flag_buf == NULL)
		ERR("flag read error");
	memset(flag_buf, 0, st.st_size + 1);
	if (st.st_size != read(flag_fd, flag_buf, st.st_size))
		ERR("flag read error");
	puts(flag_buf);
}

