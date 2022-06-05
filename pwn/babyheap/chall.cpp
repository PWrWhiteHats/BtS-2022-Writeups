#include <stdio.h>
#include <unistd.h>
#include <iostream>
#include <limits>
#include <array>

template <typename T>
void my_cin(const char *msg, T &input)
{
    while (std::cout << msg && !(std::cin >> input))
    {
        std::cin.clear();
        std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        std::cout << "Bad input!" << std::endl;
    }
}
struct String
{
    char *msg;
    unsigned int size;
};

int main()
{
    alarm(60);
    setbuf(stdin,0);
    setbuf(stdout,0);
    setbuf(stderr,0);
    std::string input;
    unsigned int index = 0, size = 0;
    std::array<String, 16> messages = {};
    std::cout << "Hello World!" << std::endl;
    while (1)
    {

        my_cin("Options:\n- add\n- remove\n- read\n- send\n- quit\n", input);
        if (input == "add")
        {
            my_cin("Index: ", index);
            my_cin("Size: ", size);
            messages.at(index) = String{new char[size], size};
        }
        else if (input == "remove")
        {
            my_cin("Index: ", index);
            delete [] messages.at(index).msg;
        }
        else if (input == "send")
        {
            my_cin("Index: ", index);
            std::cout << "Send message: " << std::endl;
            int n = read(0, messages.at(index).msg, messages.at(index).size-1);
            messages.at(index).msg[n+1] = '\0';
        }
        else if (input == "read")
        {
            my_cin("Index: ", index);
            puts(messages.at(index).msg);
        }
        else if (input == "quit")
        {
            std::cout << "Goodbye World!" << std::endl;
            break;
        }
        else
        {
            std::cout << "Try again" << std::endl;
        }
    }
}
