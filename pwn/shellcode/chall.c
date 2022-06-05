#include <sys/mman.h>
#include <sys/stat.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdio.h>
#define ERR(s)              \
	{                       \
		puts((s));        \
		exit(EXIT_FAILURE); \
	}

#define PAGE_SIZE 0x1000
int main()
{
	setbuf(stdout, NULL);
	setbuf(stdout, NULL);
	setbuf(stderr, NULL);
	alarm(15);
	int flag_fd = open("./flag.txt", O_RDONLY);
	struct stat st;
	if (-1 == fstat(flag_fd, &st))
		ERR("flag read error");

	char *flag_buf = malloc(st.st_size + 1);
	if (flag_buf == NULL)
		ERR("flag read error");

	memset(flag_buf, 0, st.st_size + 1);

	if (-1 == read(flag_fd, flag_buf, st.st_size))
		ERR("flag read error");
	close(flag_fd);

	char *buf = mmap(NULL, PAGE_SIZE, PROT_EXEC | PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS, 0, 0);
	if (buf == (void *)-1)
		ERR("mmap fail");

	puts("Waiting for shellcode...");
	ssize_t length = read(0, buf, PAGE_SIZE);
	if (-1 == length)
		ERR("read fail");

	bool modified = false;
	puts("Overwriting forbidden instructions(sysenter, int 0x80, syscall) ...");
	for (int i = 0; i < length - 1; ++i)
	{
		if (buf[i] == '\x0f')
		{
			if (buf[i + 1] == '\x34' || buf[i + 1] == '\x05') // 0f 34(sysenter) or 0f 05(syscall)
			{
				buf[i] = '\x90'; // nop
				buf[i + 1] = '\x90';
				modified = true;
			}
		}
		if (buf[i] == '\xcd')
		{
			if (buf[i + 1] == '\x80') // cd 80 (int 80)
			{
				buf[i] = '\x90'; // nop
				buf[i + 1] = '\x90';
				modified = true;
			}
		}
	}
	if (modified)
	{
		puts("Your shellcode has been modified :P");
	}
	if (-1 == mprotect(buf, PAGE_SIZE, PROT_READ | PROT_EXEC))
		ERR("mprotect fail");

	((void (*)())buf)();
	free(flag_buf);
	return 0;
}
