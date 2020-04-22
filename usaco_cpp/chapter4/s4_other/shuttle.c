
#include <stdio.h>
#include <stdbool.h>
#include <string.h>

struct queue {
    int* front_ptr;
    int* back_ptr;
};

inline struct queue* queue_new(const int capacity) {
    int array[capacity];
    memset(array, 0, sizeof(int)*capacity);
    struct queue* ptr = {array, array};
    return ptr;
}

inline int push_back(int e, struct queue* q) {
    *(q->back_ptr++) = e;
}

inline int top(struct queue* q) {
    return *(q->front_ptr);
}

inline void pop(struct queue* q) {
    ++(q->front_ptr);
}

inline bool empty(struct queue* q) {
    return q->front_ptr == q->back_ptr;
}

int main() {
    FILE* in = fopen("shuttle.in", "r");
    int N;
    fscanf(in, "%d", &N);
    fclose(in);
    const int SIZE = N*(N<<1);

    bool visited[SIZE];
    struct queue* qe = queue_new(SIZE);

    do {
        int curr_state = top(qe);
    } while(empty(qe));

    FILE* out = fopen("shuttle.out", "w");
    fclose(out);

    return 0;
}