#include "header.h"
#include "manager.h"


bool isAlpha(string str) {
  for (unsigned int i = 0; i < str.size(); i++) {
    if (isalpha(str.at(i)) == 0)
      return false;
  }
  return true;
}

bool isNumber(string str) {
  for (unsigned int i = 0; i < str.size(); i++) {
    if (isdigit(str.at(i)) == 0)
      return false;
  }
  return true;
}

// compare students name
bool ncompare(const Student& a, const Student& b) { return a.getName() < b.getName(); }
// compare students ID
bool icompare(const Student& a, const Student& b) { return a.getId() < b.getId(); }
// compare students department
bool dcompare(const Student& a, const Student& b) { return a.getDept() < b.getDept(); }

