
/*
ID: mihirsh1
TASK: fc
LANG: C++
*/

#include <cstdio>
#include <fstream>
#include <cmath>
#include <vector>
#include <algorithm>

const double INF = 2000000.0;
const double EPS = 1e-5;

class Point {
	protected:
		double x;
		double y;
	public:
		Point();
		double getX();
		double getY();
		void set(double,double);
		double angle();
		double mgn();
};

Point p0;

Point::Point() {
}

void Point::set(double x, double y) {
	this->x = x;
	this->y = y;
}

double Point::getX(){
	return x;
}

double Point::getY(){
	return y;
}

double Point::mgn() {
	const double dx = x-p0.getX();
	const double dy = y-p0.getY(); 
	return dx*dx+dy*dy;
}

double Point::angle() {
	if(fabs(y-p0.getY()) < EPS && fabs(x-p0.getX()) < EPS) {
		return 0;
	} return atan2(y-p0.getY(), x-p0.getX());
}

bool compare(Point& p1, Point& p2) {
	if(fabs(p2.angle()-p1.angle())<EPS){
		return p2.mgn()>p1.mgn();
	} else {
		return p2.angle()>p1.angle();
	}
}

bool ccw(Point* before, Point* curr, Point* after) {
	const double ang1 = atan2(curr->getY()-before->getY(), curr->getX()-before->getX());
	const double ang2 = atan2(after->getY()-curr->getY(), after->getX()-curr->getX());

	int zero1 = false; 
	int zero2 = false;
	int pi1 = false;
	int pi2 = false;
	
	if(fabs(ang1) < EPS) {
		zero1 = true;
	} else if(fabs(ang1-M_PI) < EPS) {
		pi1 = true;
	}
	if(fabs(ang2) < EPS) {
		zero2 = true;
	} else if(fabs(ang2-M_PI) < EPS) {
		pi2 = true;
	}

	if((zero1 || pi1) && (zero2 || pi2)) {
		return true;
	}
	if(zero1) {
		return ang2 > 0;
	}
	if(pi1) {
		return ang2 < 0;
	}
	if(zero2) {
		return ang1 < 0;
	}
	if(pi2) {
		return ang1 > 0;
	}
	if(ang1 > 0 ^ ang2 > 0) {
		if(ang1 > 0) {
			return ang1-M_PI >= ang2;
		} else {
			return ang1+M_PI >= ang2;
		}
	} else {
		return ang1 <= ang2;
	}
	// should never get here
	return false;
}

bool leftdown(Point* p1, Point* p2) {
	if(p1->getY() > p2->getY()){
		return true;
	} else if(fabs(p2->getY()-p1->getY())<EPS) {
		return p1->getX() > p2->getX();
	} return false;
}

double dist(Point* p1, Point* p2) {
	const double xp = p1->getX()-p2->getX();
	const double yp = p1->getY()-p2->getY();
	return sqrt(xp*xp+yp*yp);
}

int main() {
	using namespace std;
	int buf;
	double buf1, buf2;
	
	ifstream fin("fc.in");
	fin >> buf;
	const int N = buf;
	Point points[N];
	
	for(int i = 0; i<N; ++i) {
		fin >> buf1 >> buf2;
		points[i].set(buf1, buf2);
	}
	
	fin.close();
	p0 = points[0];
	
	// marginal cost of copying p0
	for(int i = 0; i<N; ++i) {
		if(leftdown(&p0, &points[i])) {
			p0 = points[i];
		}
	}
	
	sort(points, points+N, compare);
	vector<Point*> stk;

	/*
	for(int i = 0; i<N; ++i) {
		printf("%f %f\n", points[i].getX(), points[i].getY());
	}
	printf("\n");
	*/

	for(Point* ptr = points; ptr!=points+N; ++ptr) {
		int size;
		while((size = stk.size())>1 && 
				!ccw(stk[size-2], stk[size-1], ptr)) {
			stk.pop_back();
		}
		stk.push_back(ptr);
		/*
		for(int i = 0; i<stk.size(); ++i) {
			printf("%f %f\n", stk[i]->getX(), stk[i]->getY());
		}
		printf("\n");
		*/
	}

	// get front and back
	double output = dist(stk[0], stk[stk.size()-1]);
	const int size = stk.size();
	for(int i = 0; i<size-1; ++i) {
		output += dist(stk[i], stk[i+1]);
	}

	FILE* fout = fopen("fc.out", "w");
	fprintf(fout, "%.2f\n", output);
	fflush(fout);
	fclose(fout);
}
