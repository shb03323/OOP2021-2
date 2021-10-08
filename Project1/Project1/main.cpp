#include "UI.h"

// main menu
int main(int argc, char **argv) {
  StudentManageUI main = StudentManageUI();
  if (argc != 2) {
    cout << "no file";
    return (0);
  }
  main.getFile(argv[1]);
  main.mainUI();
}
