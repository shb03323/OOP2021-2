#ifndef MANAGER_H
#define MANAGER_H

#include "header.h"

class Student {
private:
  string name, id, birth_year, tel, dept;
public:
  Student(string _name, string _id, string _birth_year, string _tel, string _dept)
    : name(_name), id(_id), birth_year(_birth_year), tel(_tel), dept(_dept) { }
  string getName() const { return name; }
  string getId() const { return id; }
  string getBirth() const { return birth_year; }
  string getDept() const { return dept; }
  string getTel() const { return tel; }
};

class Manager {
private:
  vector<Student> sVector;
  int size;
  int s_def;
public:
  Manager() : s_def(0), size(0) {}
  
  void readData(char *filename);
  
  bool isAlpha(string str);
  
  bool isNumber(string str);

  bool check_id_dup(string id);

  void putData(Student s);

  void sortData(int option);
  void printData(int width);

  void printSearchData(string keyword, int option);

  vector<Student> showData() { return sVector; }
};



#endif
