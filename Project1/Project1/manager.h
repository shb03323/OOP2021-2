#ifndef MANAGER_H
#define MANAGER_H

#include "header.h"



class Student {
private:
  string name, id, birth_year, tel, dept;
public:
  // Student Object
  Student(string _name, string _id, string _birth_year, string _tel, string _dept)
    : name(_name), id(_id), birth_year(_birth_year), tel(_tel), dept(_dept) { }

  // get student's name
  string getName() const { return name; }
  // get student's ID
  string getId() const { return id; }
  // get student's birth
  string getBirth() const { return birth_year; }
  // get student's department
  string getDept() const { return dept; }
  // get student's phone number
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
  // check if string is contains of alphabets
  bool isAlpha(string str);
  // check if string is contains of numbers
  bool isNumber(string str);

  bool check_id_dup(string id);

  void putData(Student s);

  void sortData(int option);
  void printData(int width);

  vector<Student> showData() { return sVector; }
};



#endif
