
#include "manager.h"
#include "utility.h"
#include "UI.h"


UI::UI(int num)
{
  menu_num = num;
  menu.resize(num);
}

void UI::printUI() {
  system("cls");
  for (int i = 0; i < menu_num; i++) {
    cout << menu[i] << endl;
  }
}

void UI::printUI(int width) {
  system("cls");
  cout.setf(ios::left);
  cout << "\n" << setw(width) << menu[0] << setw(width) << menu[1] << setw(width) << menu[2] << setw(width) << menu[3] << setw(width) << menu[4] << endl;
  cout << "--------------------------------------------------------------------------------------" << endl;
}

void UI::insertMenu(string str){
  menu.push_back(str);
  menu_num = menu.size();
}


Student UserInputUI::userInput(Manager& manager) {
  string name, id, birth_year, tel, dept;
  ofstream fo;
  fo.open("file1.txt", ios::out | ios::app);
  // check name
  while (1) {
    cout << menu[0] << endl;
    getline(cin, name);
    // make tempName string for erase space of name string
    string tempName = name;
    tempName.erase(remove(tempName.begin(), tempName.end(), ' '), tempName.end());

    // name size must be less than 16 characters
    if (tempName.size() > 15)
      cout << "Name has up to 15 English characters." << endl;
    // name must be composed of alphabets
    else if (!isAlpha(tempName))
      cout << "Name must be composed of alphabet." << endl;
    // no error
    else
      break;
  }
  //checking student ID
  while (1) {
    cout << menu[1] << endl;
    getline(cin, id);

    // student ID must be integer
    if (!isNumber(id))
      cout << "student ID must composed of digits." << endl;
    // student ID must be exact 10 digits
    else if (id.size() != 10)
      cout << "student ID must be exactly 10 digits." << endl;
    // first 4 digits must be admission year
    else if (stoi(id.substr(0, 4)) < 1950 && stoi(id.substr(0, 4)) > 2022)
      cout << "first 4 digits must be admission year" << endl;
    // cheking ID already inserted
    else {
      if(manager.check_id_dup(id))
        cout << "Error : Already inserted" << endl;
      else break;
    }


  }
  //checking birthYear
  while (1) {
    cout << menu[2] << endl;
    getline(cin, birth_year);

    // empty value
    if (birth_year.empty()) {
      birth_year = " ";
      break;
    }
    // birthYear must be integer
    else if (!isNumber(birth_year))
      cout << "birth year must composed of digits." << endl;
    // birthYear must be exact 4 digits
    else if (birth_year.size() != 4)
      cout << "birth year must be exactly 4 digits." << endl;
    // birthYear must be smaller than 2022, bigger than 1900
    else if (stoi(birth_year) > 2021 && stoi(birth_year) <= 1900)
      cout << "I think you did not birth at that year." << endl;
    // no error
    else
      break;
  }
  //input department
  cout << menu[3] << endl;
  getline(cin, dept);
  if (dept.empty())
    dept = " ";
  // checking tel
  while (1) {
    cout << menu[4] << endl;
    getline(cin, tel);

    // empty value
    if (tel.empty()) {
      tel = " ";
      break;
    }
    // tel must be integer
    else if (!isNumber(tel))
      cout << "telephone number must composed of digits." << endl;
    // tel size must be less than 13 digits
    else if (tel.size() > 12)
      cout << "telephone number has up to 12 digits." << endl;
    // no error
    else
      break;
  }

  fo << name << endl;
  fo << id << endl;
  fo << birth_year << endl;
  fo << tel << endl;
  fo << dept << endl;
  fo.close();

  cout << endl << menu[5] << endl;

  return (Student(name, id, birth_year, tel, dept));
}

StudentManageUI::StudentManageUI()
{
  main_menu.insertMenu("1. Insertion");
  main_menu.insertMenu("2. Serach");
  main_menu.insertMenu("3. Sorting Option");
  main_menu.insertMenu("4. Exit");

  search_menu.insertMenu(" - Search -");
  search_menu.insertMenu("1. Search by name");
  search_menu.insertMenu("2. Serach by student ID (10 numbers)");
  search_menu.insertMenu("3. Search by admission year (4 numbers)");
  search_menu.insertMenu("4. Search by department name");
  search_menu.insertMenu("5. List All");

  put_data.insert("Name ? ");
  put_data.insert("Student ID (10 digits) ? ");
  put_data.insert("Birth Year (4 digits) ? ");
  put_data.insert("Department ? ");
  put_data.insert("Tel ? ");
  put_data.insert("Insertion Complete!");

  print_search.insertMenu("Name");
  print_search.insertMenu("Student ID");
  print_search.insertMenu("Department");
  print_search.insertMenu("Birth Year");
  print_search.insertMenu("Tel");

  search_process.insertMenu("Name");
  search_process.insertMenu("Student ID");
  search_process.insertMenu("Department");
  search_process.insertMenu("Birth Year");
  search_process.insertMenu("Tel");

  search_process.insertGuide("\nName Keyword? ");
  search_process.insertGuide("\nStudent ID(10 numbers) Keyword? ");
  search_process.insertGuide("\nAdmission Year(4 numbers) Keyword? ");
  search_process.insertGuide("\nDepartment Name Keyword? ");

  sort_menu.insertMenu("- Sorting Option");
  sort_menu.insertMenu("1. Sort by Name");
  sort_menu.insertMenu("2. Sort by Student ID");
  sort_menu.insertMenu("3. Sort by Admission Year");
  sort_menu.insertMenu("4. Sort by Department name");
  sort_menu.insertMenu("> ");
}

void StudentManageUI::getFile(char *input) {
  file_name = input;
  manager.readData(input);
}

int StudentManageUI::userInput() {
  cout << endl << "> ";
  // input search number
  int in;
  cin >> in;
  cin.ignore(1);
  cout << " " << endl;
  return in;
}


void Option::print_result(int option, vector<Student> sVector) {
  printUI(18);
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

void StudentManageUI::mainUI() {
  int in;
  int option;
  while (1) {
    system("cls");
    main_menu.printUI();
    
    // input selection
    in = userInput();

    system("cls");
    switch (in) { 
    case 1: {
      manager.putData(put_data.userInput(manager));
      break;
    }
    case 2: {
      search_menu.printUI();
      option = userInput();
      string keyword;
      if (option != 5) {
        search_process.print_guide(option);
        cin >> keyword;
      }
      search_process.printUI(18);
      manager.printSearchData(keyword, option);
      break;
    }
    case 3: {
      sort_menu.printUI();
      option = userInput();
      manager.sortData(option);
      print_search.printUI(18);
      manager.printData(18);
      to_home();
      break;
      
    }
    case 4: {
      cout << "Exit the Program" << endl;
      Sleep(1000);
      return;
    }
    }
  }
}
void to_home() {
  cout << "\nPress enter key to exit." << endl;
  char exitbtn;
  while (1) {
    cin.get(exitbtn);
    cin.ignore();
    if (exitbtn == '\n')
      return;
  }
}
