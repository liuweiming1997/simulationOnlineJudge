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
const int maxn = 1000000 + 20;
int n;
int a[maxn];
LL sum[maxn];
LL nowDel;
LL ask(int pos) {
    if (pos < nowDel) {
        return sum[pos] - a[pos] + a[nowDel];
    } else return sum[pos];
}
bool tofind() {
    int be = 2, en = n;
    while (be <= en) {
        int mid = (be + en) >> 1;
        LL lef = ask(mid - 1);
        LL rig = sum[n] - lef;
        if (lef < rig) be = mid + 1;
        else en = mid - 1;
    }
    return ask(en) * 2 == sum[n];
}
void work() {
    scanf("%d", &n);
    for (int i = 1; i <= n; ++i) {
        scanf("%d", &a[i]);
        sum[i] = sum[i - 1] + a[i];
    }
//    nowDel = 3;
//    tofind();
//    exit(0);
    for (int i = 1; i <= n; ++i) {
        nowDel = i;
        if (tofind()) {
            printf("YES\n");
            return;
        }
    }
    printf("NO\n");
}

int main() {freopen("data.txt", "r", stdin);freopen("dataOutMyCode.txt", "w", stdout);
#ifdef local
    freopen("data.txt", "r", stdin);
//    freopen("data.txt", "w", stdout);
#endif
    work();
    return 0;
}
