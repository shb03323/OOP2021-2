#include "manager.h"
#include "utility.h"
#include "header.h"

/*
// compare students name
bool ncompare(const Student& a, const Student& b) { return a.getName() < b.getName(); }
// compare students ID
bool icompare(const Student& a, const Student& b) { return a.getId() < b.getId(); }
// compare students department
bool dcompare(const Student& a, const Student& b) { return a.getDept() < b.getDept(); }
*/


// check string consists of alphabets
bool Manager::isAlpha(string str) {
  for (unsigned int i = 0; i < str.size(); i++) {
    if (isalpha(str.at(i)) == 0)
      return false;
  }
  return true;
}

// check string consists of digits
bool Manager::isNumber(string str) {
  for (unsigned int i = 0; i < str.size(); i++) {
    if (isdigit(str.at(i)) == 0)
      return false;
  }
  return true;
}

// put student data to file

// read file data
void Manager::readData(char *file_name) {
  // file's each lines
  string line;
  // array for student data
  string data[5];

  ifstream fi;
  fi.open(file_name , ios::in);
  fi.seekg(ios::beg);

  if (fi.is_open()) {
    while (1) {
      for (int i = 0; i < 5; i++) {
        if (getline(fi, line))
          data[i] = line;
        else {
          fi.close();
          return;
        }
        if (i == 4) {
          sVector.push_back(Student(data[0], data[1], data[2], data[3], data[4]));
          size++;
        }
      }
    }
  }
}

bool Manager::check_id_dup(string id) {
  if (!sVector.empty()) {
    for (Student s : sVector) {
      if (s.getId() == id) {
        return true;
      }
    }
  }
  else return false;
}

void Manager::putData(Student s) {
  sVector.push_back(s);
}

void Manager::sortData(int option)
{
  switch (option) {
  case 1: {
    sort(sVector.begin(), sVector.end(), ncompare);
    break;
  }
  case 2 & 3: {
    sort(sVector.begin(), sVector.end(), icompare);
    break;
  }
  case 4: {
    sort(sVector.begin(), sVector.end(), dcompare);
    break;
  }
  }

}


void Manager::printData(int width) {
  for (Student s : sVector) {
    cout << setw(width) << s.getName() << setw(width) << s.getId() << setw(width) << s.getDept() << setw(width) << s.getBirth() << setw(width) << s.getTel() << endl;
  }
}
