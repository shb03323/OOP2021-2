#ifndef UI_H
#define UI_H

#include "header.h"
#include "manager.h"

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

class Option : UI {
public:
  Option() {};
  void print_guide(int selection) {
    cout << guide_out[selection] << endl;
  }
  void to_search() {
    cin >> keyword;
  }
  void print_result(int option, vector<Student> sVector);
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
