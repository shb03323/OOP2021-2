#include "manager.h"
#include "utility.h"
#include "header.h"


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
      bool isIns = false;
      for (Student s : sVector) {
          if (s.getId() == id) {
              isIns = true;
              return true;
          }
      }
      if (!isIns) return false;
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
    case 2: case 3: {
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

void Manager::printSearchData(string keyword, int option) {
  switch (option) {
  case 1: {
    int cnt = 0;
    for (Student s : sVector) {
      if (s.getName().compare(keyword) == 0) {
        cout << setw(18) << s.getName() << setw(18) << s.getId() << setw(18) << s.getDept() << setw(18) << s.getBirth() << setw(18) << s.getTel() << endl;
        cnt++;
      }
    }
    if (cnt == 0)
      cout << "Nothing to Show" << endl;

    cout << "\nPress enter key to exit." << endl;
    char exitbtn;
    cin.ignore();
    cin.get(exitbtn);
    if (exitbtn == '\n')
      break;
  }
        // search by name
  case 2: {
    int cnt = 0;
    for (Student s : sVector) {
      if (s.getId().compare(keyword) == 0) {
        cout << setw(18) << s.getName() << setw(18) << s.getId() << setw(18) << s.getDept() << setw(18) << s.getBirth() << setw(18) << s.getTel() << endl;
        cnt++;
      }
    }
    if (cnt == 0)
      cout << "Nothing to Show" << endl;

    cout << "\nPress enter key to exit." << endl;
    char exitbtn;
    cin.ignore();
    cin.get(exitbtn);
    if (exitbtn == '\n')
      break;
  }
        // search by ID
  case 3: {
    int cnt = 0;
    for (Student s : sVector) {
      if (s.getId().substr(0, 4).compare(keyword) == 0) {
        cout << setw(18) << s.getName() << setw(18) << s.getId() << setw(18) << s.getDept() << setw(18) << s.getBirth() << setw(18) << s.getTel() << endl;
        cnt++;
      }
    }
    if (cnt == 0)
      cout << "Nothing to Show" << endl;

    cout << "\nPress enter key to exit." << endl;
    char exitbtn;
    cin.ignore();
    cin.get(exitbtn);
    if (exitbtn == '\n')
      break;
  }
        // search by admission year
  case 4: {
    int cnt = 0;
    for (Student s : sVector) {
      if (s.getDept().compare(keyword) == 0) {
        cout << setw(18) << s.getName() << setw(18) << s.getId() << setw(18) << s.getDept() << setw(18) << s.getBirth() << setw(18) << s.getTel() << endl;
        cnt++;
      }
    }
    if (cnt == 0)
      cout << "Nothing to Show" << endl;

    cout << "\nPress enter key to exit." << endl;
    char exitbtn;
    cin.ignore();
    cin.get(exitbtn);
    if (exitbtn == '\n')
      break;
  }
        //search by department
  case 5: {
    for (Student s : sVector)
      cout << setw(18) << s.getName() << setw(18) << s.getId() << setw(18) << s.getDept() << setw(18) << s.getBirth() << setw(18) << s.getTel() << endl;

    cout << "\nPress enter key to exit." << endl;
    char exitbtn;
    cin.ignore();
    cin.get(exitbtn);
    if (exitbtn == '\n')
      break;
  }
        // show all
  }
}
