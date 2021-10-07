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
		Student(char _name[], char _id[], char _birth_year[], char _tel[], string _dept)
			: name(_name), id(_id) , birth_year(_birth_year),  tel(_tel), dept(_dept) { }
		Student(string _name, string _id, string _birth_year, string _tel, string _dept)
			: name(_name), id(_id), birth_year(_birth_year), tel(_tel), dept(_dept) { }
		string getName() const { return name; }
		string getId() const { return id; }
		string getBirth() const { return birth_year; }
		string getDept() const { return dept; }
		string getTel() const { return tel; }
};

vector<Student> sVector;
int s_def = 0;

Student putData() {
	char name[16], id[11], birth_year[5], tel[13];
	string dept;
	ofstream fo;
	fo.open("file1.txt", ios::out | ios::app);

	if (fo.is_open()) {
		cin.ignore(1);
		cout << "Name ? ";
		cin.getline(name, 16);
		cout << "Student ID (10 digits) ? ";
		cin.getline(id, 11);
		if (!sVector.empty()) {
			for (Student s : sVector) {
				if (s.getId() == id) {
					cout << "Error : Already inserted" << endl;
				}
			}
		}
		cout << "Birth Year (4 digits) ? ";
		cin.getline(birth_year, 5);
	
		cout << "Department ? ";
		getline(cin, dept);
		cout << "Tel ? ";
		cin.getline(tel, 13);
		fo << name << endl;
		fo << id << endl;
		fo << birth_year << endl;
		fo << tel << endl;
		fo << dept << endl;
		fo.close();
		
		sVector.push_back(Student(name, id, birth_year, tel, dept));

		cout << " " << endl;
		cout << "Insertion Complete" << endl;
		Sleep(3000);
	}
	else cout << "Unable to open file" << endl;

	return Student(name, id, birth_year, tel, dept);
}

void readData() {
	string line;
	string data[5];
	ifstream fi;
	fi.open("file1.txt", ios:: in);
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

bool ncompare(const Student& a, const Student& b) {
	return a.getName() < b.getName();
}

bool icompare(const Student& a, const Student& b) {
	return a.getId() < b.getId();
}

bool dcompare(const Student& a, const Student& b) {
	return a.getDept() < b.getDept();
}


void printMenu() {
	cout << "1. Insertion" << endl;
	cout << "2. Serach" << endl;
	cout << "3. Sorting Option" << endl;
	cout << "4. Exit" << endl;
}

void searchMenu() {
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
			cout << " " << endl;
			cout << "Name Keyword? ";
			cin >> name;
			cout << "Name			Student ID Dept			Birth Year	Tel" << endl;
			for (Student s : sVector) {
				if (s.getName().compare(name) == 0) {
					cout << s.getName() << " " << s.getId() << " " << s.getDept() << " " << s.getBirth() << s.getTel() << endl;
					Sleep(3000);
				}
			}
			break;
		}
		case 2: {
			string id;
			cout << " " << endl;
			cout << "Student ID(10 numbers) Keyword? ";
			cin >> id;
			cout << "Name			Student ID Dept			Birth Year	Tel" << endl;
			for (Student s : sVector) {
				if (s.getId().compare(id) == 0) {
					cout << s.getName() << " " << s.getId() << " " << s.getDept() << " " << s.getBirth() << s.getTel() << endl;
					Sleep(3000);
				}
			}
			break;
		}
		case 3: {
			string year;
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
			break;
		}
		case 4: {
			string dept;
			cout << " " << endl;
			cout << "Department Name Keyword? ";
			cin >> dept;
			cout << "Name			Student ID Dept			Birth Year	Tel" << endl;
			for (Student s : sVector) {
				if (s.getDept().compare(dept) == 0) {
					cout << s.getName() << " " << s.getId() << " " << s.getDept() << " " << s.getBirth() << s.getTel() << endl;
					Sleep(3000);
				}
			}
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

void sortMenu() {
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
		case 2&3: {
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



int main(int argc, char **argv) {
	readData();
	while (1) {
		system("cls");
		printMenu();
		cout << "> ";
		int in;
		cin >> in;
		switch (in) {
			case 1: {
				system("cls");
				Student student = putData();
				break;
			}
			case 2: {
				system("cls");
				searchMenu();
				break;
			}
			case 3: {
				system("cls");
				sortMenu();
				break;
			}
			case 4: {
				cout << "Exit the Program" << endl;
				return 0;
			}
		}
	}
}
