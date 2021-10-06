#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <windows.h>
#include <algorithm>
using namespace std;

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
	int s_def;
public:
	Manager() : s_def(0) {}
	Student putData();
	void readData();
	void printMenu();
	void searchMenu();
	void sortMenu();
	bool isAlpha(string str);
	bool isNumber(string str);
};

bool ncompare(const Student& a, const Student& b) { return a.getName() < b.getName(); }
bool icompare(const Student& a, const Student& b) { return a.getId() < b.getId(); }
bool dcompare(const Student& a, const Student& b) { return a.getDept() < b.getDept(); }

// check string consists of alphabets
bool Manager::isAlpha(string str) {
	for (int i = 0; i < str.size(); i++) {
		if (isalpha(str.at(i)) == 0)
			return false;
	}
	return true;
}

// check string consists of digits
bool Manager::isNumber(string str) {
	for (int i = 0; i < str.size(); i++) {
		if (isdigit(str.at(i)) == 0)
			return false;
	}
	return true;
}

// put student data to file
Student Manager::putData() {
	string name, id, birth_year, tel, dept;
	ofstream fo;
	fo.open("file1.txt", ios::out | ios::app);

	if (fo.is_open()) {
		cin.ignore(1);
		// checking name
		while (1) {
			cout << "Name ? ";
			getline(cin, name);
			// name size must be less than 16 characters
			if (name.size() > 15)
				cout << "Name has up to 15 English characters." << endl;
			// name must be composed of alphabets
			else if (!isAlpha(name))
				cout << "Name must be composed of alphabet." << endl;
			// no error
			else break;
		}

		// checking student ID
		while (1) {
			cout << "Student ID (10 digits) ? ";
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
				if (!sVector.empty()) {
					for (Student s : sVector) {
						if (s.getId() == id)
							cout << "Error : Already inserted" << endl;
					}
					break;
				}
				else break;
			}

		}

		// checking birthYear
		while (1) {
			cout << "Birth Year (4 digits) ? ";
			getline(cin, birth_year);

			// birthYear must be integer
			if (!isNumber(birth_year))
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
		cout << "Department ? ";
		getline(cin, dept);
		// checking tel
		while (1) {
			cout << "Tel ? ";
			getline(cin, tel);

			// tel must be integer
			if (!isNumber(tel))
				cerr << "telephone number must composed of digits." << endl;
			// tel size must be less than 13 digits
			else if (tel.size() > 12)
				cerr << "telephone number has up to 12 digits." << endl;
			else
				break;
		}

		// write student info to the file
		fo << name << endl;
		fo << id << endl;
		fo << birth_year << endl;
		fo << tel << endl;
		fo << dept << endl;
		fo.close();

		// push info to manage vector
		sVector.push_back(Student(name, id, birth_year, tel, dept));

		cout << " " << endl;
		cout << "Insertion Complete" << endl;
		Sleep(3000);
	}
	else cout << "Unable to open file" << endl;

	return Student(name, id, birth_year, tel, dept);
}

void Manager::readData() {
	string line;
	string data[5];
	ifstream fi;
	fi.open("file1.txt", ios::in);
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
				if (i == 4)
					sVector.push_back(Student(data[0], data[1], data[2], data[3], data[4]));
			}
		}
	}
}

// Menu
void Manager::printMenu() {
	cout << "1. Insertion" << endl;
	cout << "2. Serach" << endl;
	cout << "3. Sorting Option" << endl;
	cout << "4. Exit" << endl;
}

void Manager::searchMenu() {
	cout << " - Search -" << endl;
	cout << "1. Search by name" << endl;
	cout << "2. Serach by student ID (10 numbers)" << endl;
	cout << "3. Search by admission year (4 numbers)" << endl;
	cout << "4. Search by department name" << endl;
	cout << "5. List All" << endl;
	cout << " " << endl;
	cout << "> ";
	int in;
	cin >> in;
	cin.ignore(1);
	cout << " " << endl;

	if (s_def == 0)
		sort(sVector.begin(), sVector.end(), ncompare);

	switch (in) {
	case 1: {
		string name;
		int cnt = 0;
		cout << " " << endl;
		cout << "Name Keyword? ";
		cin >> name;
		cout << "Name			Student ID Dept			Birth Year	Tel" << endl;
		for (Student s : sVector) {
			if (s.getName().compare(name) == 0) {
				cout << s.getName() << " " << s.getId() << " " << s.getDept() << " " << s.getBirth() << s.getTel() << endl;
				cnt++;
				Sleep(3000);
			}
		}
		if (cnt == 0)
			cout << "Nothing to Show" << endl;
		break;
	}
	case 2: {
		string id;
		int cnt = 0;
		cout << " " << endl;
		cout << "Student ID(10 numbers) Keyword? ";
		cin >> id;
		cout << "Name			Student ID Dept			Birth Year	Tel" << endl;
		for (Student s : sVector) {
			if (s.getId().compare(id) == 0) {
				cout << s.getName() << " " << s.getId() << " " << s.getDept() << " " << s.getBirth() << s.getTel() << endl;
				cnt++;
				Sleep(3000);
			}
		}
		if (cnt == 0)
			cout << "Nothing to Show" << endl;
		break;
	}
	case 3: {
		string year;
		int cnt = 0;
		cout << " " << endl;
		cout << "Admission Year(4 numbers) Keyword? ";
		cin >> year;
		cout << "Name			Student ID Dept			Birth Year	Tel" << endl;
		for (Student s : sVector) {
			if (s.getId().substr(0, 4).compare(year) == 0) {
				cout << s.getName() << " " << s.getId() << " " << s.getDept() << " " << s.getBirth() << s.getTel() << endl;
				Sleep(3000);
			}
		}
		if (cnt == 0)
			cout << "Nothing to Show" << endl;
		break;
	}
	case 4: {
		string dept;
		int cnt = 0;
		cout << " " << endl;
		cout << "Department Name Keyword? ";
		cin >> dept;
		cout << "Name			Student ID Dept			Birth Year	Tel" << endl;
		for (Student s : sVector) {
			if (s.getDept().compare(dept) == 0) {
				cout << s.getName() << " " << s.getId() << " " << s.getDept() << " " << s.getBirth() << s.getTel() << endl;
				cnt++;
				Sleep(3000);
			}
		}
		if (cnt == 0)
			cout << "Nothing to Show" << endl;
		break;
	}
	case 5: {
		cout << " " << endl;
		cout << "Name			Student ID Dept			Birth Year	Tel" << endl;
		for (Student s : sVector)
			cout << s.getName() << " " << s.getId() << " " << s.getDept() << " " << s.getBirth() << s.getTel() << endl;
		Sleep(3000);
	}

	}
}

void Manager::sortMenu() {
	cout << "- Sorting Option" << endl;
	cout << "1. Sort by Name" << endl;
	cout << "2. Sort by Student ID" << endl;
	cout << "3. Sort by Admission Year" << endl;
	cout << "4. Sort by Department name" << endl;
	cout << "> ";
	int in;
	cin >> in;
	cin.ignore(1);
	switch (in) {
	case 1: {
		sort(sVector.begin(), sVector.end(), ncompare);
		s_def = 1;
		break;
	}
	case 2 & 3: {
		sort(sVector.begin(), sVector.end(), icompare);
		s_def = 2;
		break;
	}
	case 4: {
		sort(sVector.begin(), sVector.end(), dcompare);
		s_def = 4;
		break;
	}
	}
}
int main() {
	Manager manager = Manager();
	manager.readData();
	while (1) {
		system("cls");
		manager.printMenu();
		cout << "> ";
		short in;
		cin >> in;
		switch (in) {
		case 1: {
			system("cls");
			Student student = manager.putData();
			break;
		}
		case 2: {
			system("cls");
			manager.searchMenu();
			break;
		}
		case 3: {
			system("cls");
			manager.sortMenu();
			break;
		}
		case 4: {
			cout << "Exit the Program" << endl;
			return 0;
		}
		}
	}
}