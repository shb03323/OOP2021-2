#include <iostream>
#include <fstream>
#include <string>
#include <cctype>
using namespace std;

void insertion();
bool isAlphabet(string name);
bool isNumber(string str);

class Student {
public:
	string name;
	string studentID;
	string birthYear; //��Ȯ�� 4����
	string department;
	string tel; //�ִ� 12����
};

// 1. Insertion
void insertion() {
	string name;
	string studentID;
	string birthYear;
	string department;
	string tel;

	// checking name
	while (1) {
		cerr << "name : ";
		cin >> name;

		// name size must be less than 16 characters
		if (name.size() > 15)
			cerr << "Name has up to 15 English characters." << endl;

		// name must be composed of alphabets
		else if (!isAlphabet(name))
			cerr << "Name must be composed of alphabets." << endl;

		// no error
		else
			break;
	}

	// checking student ID
	while (1) {
		cerr << "student ID : ";
		cin >> studentID;

		// student ID must be integer
		if (!isNumber(studentID))
			cerr << "student ID must composed of digits." << endl;

		// student ID must be exact 10 digits
		else if (studentID.size() != 10) {
			cerr << "student ID must be exactly 10 digits." << endl;
		}

		// first 4 digits must be admission year
		else if (stoi(studentID.substr(0, 4)) < 1950 && stoi(studentID.substr(0, 4)) > 2022)
			cerr << "first 4 digits must be admission year" << endl;

		// no error
		else
			break;
	}

	// checking birthYear
	while (1) {
		cerr << "birth year : ";
		cin >> birthYear;

		// birthYear must be integer
		if (!isNumber(birthYear))
			cerr << "birth year must composed of digits." << endl;

		// birthYear must be exact 4 digits
		else if (birthYear.size() != 4)
			cerr << "birth year must be exactly 4 digits." << endl;

		// birthYear must be smaller than 2022, bigger than 1900
		else if (stoi(birthYear) > 2021 && stoi(birthYear) <= 1900)
			cerr << "I think you did not birth at that year." << endl;

		// no error
		else
			break;
	}

	// input department
	cerr << "department : ";
	cin >> department;

	if (department.size() == 0)


	// checking tel
	while (1) {
		cerr << "tel : ";
		cin >> tel;

		// tel must be integer
		if (!isNumber(tel))
			cerr << "telephone number must composed of digits." << endl;

		// tel size must be less than 13 digits
		else if (tel.size() > 12)
			cerr << "telephone number has up to 12 digits." << endl;

		else
			break;
	}

	Student student = { name, studentID, birthYear, department, tel };
	ofstream studentFile("file1.txt", ios::app);
	studentFile << name << "|" << studentID << "|" << birthYear << "|" << department << "|" << tel << endl;
	studentFile.close();

	cerr << "Insertion success!!" << endl;
}

// check string consists of alphabets
bool isAlphabet(string str) {
	for (int i = 0; i < str.size(); i++) {
		if (isalpha(str.at(i)) == 0)
			return false;
	}
	return true;
}

// check string consists of digits
bool isNumber(string str) {
	for (int i = 0; i < str.size(); i++) {
		if (isdigit(str.at(i)) == 0)
			return false;
	}
	return true;
}

int main() {
	while (1) {
		cerr << "----Main Menu----" << endl;
		cerr << "1. Insertion" << endl;
		cerr << "2. Search" << endl;
		cerr << "3. Sorting Option" << endl;
		cerr << "4. Exit" << endl;
		cerr << "Your choice? : ";

		short selectNum;
		cin >> selectNum;

		if (selectNum == 1)
			insertion();
	}
	system("pause");
	return 0;
}