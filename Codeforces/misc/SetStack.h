
#ifndef "SET_STACK_H"
#define "SET_STACK_H"

class SetStack {
    private:
        vector<int> stack;
        vector<bool> set;
    public:
        SetStack() {
        }
        ~SetStack(){
        }
        void resize(int N) {
            set.resize(N);
        }
        void push(int e){
            stack.push_back(e);
            set[e] = true;
        }
        void pop(){
            set[stack.back()] = false;
            stack.pop_back();
        }
        int top(){
            return stack.back();
        }
        int operator [] (int idx){
            return stack[idx];
        }
        int size(){
            return stack.size();
        }
        bool empty(){
            return stack.empty();
        }
        bool find(int e){
            return set[e];
        }
};

#endif