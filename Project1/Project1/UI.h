#ifndef UI_H
#define UI_H

#include "header.h"
#include "manager.h"

void to_home();

class UI{
public:
  UI() {};
  UI(int size);
  void insertMenu(string str);
  void printUI();
  void printUI(int width);
protected:
  vector<string> menu;
  string input_buffer;
  int menu_num;
};

class Option : public UI {
public:
  Option() {};
  void print_guide(int selection) {
    cout << guide_out[selection - 1] << endl;
  }
  void to_search() {
    cin >> keyword;
  }
  void insertGuide(string str) {
    guide_out.push_back(str);
  }
  void insertMenu(string str) { menu.push_back(str); }
private:
  vector<string> guide_out;
  string keyword;
};

class UserInputUI : UI {
public:
  UserInputUI() {};
  Student userInput(Manager& manager);
  void insert(string str) {
    menu.push_back(str);
  }
};

class StudentManageUI{
public:
  StudentManageUI();
  void mainUI();
  int userInput();
  void getFile(char* file_name);
private:
  UI main_menu;
  UI search_menu;
  UI print_search;
  UI sort_menu;
  
  UserInputUI put_data;
  Option search_process;
  Manager manager;
  string file_name;
};


#endif
