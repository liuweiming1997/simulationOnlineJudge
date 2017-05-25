#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <cmath>
#include <algorithm>
#include <assert.h>
#define IOS ios::sync_with_stdio(false)
using namespace std;
#define inf (0x3f3f3f3f)
typedef long long int LL;


#include <iostream>
#include <sstream>
#include <vector>
#include <set>
#include <map>
#include <queue>
#include <string>
#include <bitset>
#include <time.h>

int main() {freopen("data.txt", "w", stdout);
#ifdef local
    freopen("data.txt", "r", stdin);
//    freopen("data.txt", "w", stdout);
#endif
    srand(time(NULL));
    int n = rand() % 1000 + 1;
    printf("%d\n", n);
    while (n--) {
        int a = rand() % 1000000000 + 1;
        printf("%d ", a);
    }
    return 0;
}
