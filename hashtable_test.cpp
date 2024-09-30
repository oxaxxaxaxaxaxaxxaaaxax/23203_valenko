#include "hashtable.cpp"
#include "googletest/googletest/include/gtest/gtest.h"
using namespace std;
//static constexpr int COUNTER = 6;

TEST(HTtest, test_1) {
  HashTable a;
  Value v = {19,58};
  int res = a.insert("Oksana", v);
  EXPECT_TRUE(v == a.operator[]("Oksana") && res ==1);
}

TEST(HTtest, test_2) {
  HashTable a;
  Value v1 = {19, 58};
  Value v2 = {22, 67};
  int res_1 = a.insert("Oksana", v1);
  int res_2 = a.insert("Oksana", v2);
  EXPECT_TRUE(v1 == a.operator[]("Oksana") && res_2 == 0);
}

TEST(HTtest, test_4) {
  HashTable a;
  Value v1 = {19, 58};
  Value v2 = {22, 67};
  int res_1 = a.insert("Oksana", v1);
  int res_2 = a.insert("Olesya", v2);
  a.clear();
  EXPECT_TRUE(a.operator[]("Oksana")== a.operator[]("Olesya"));
}

TEST(HTtest, test_5){
  HashTable a;
  Value v5 = {15, 55};
  Key k5 = "Tonya";
  Value v6 = {18, 69};
  Key k6 = "Sasha";
  int res= a.insert(k5,v5);
  res+= a.insert(k6,v6);
  HashTable b;
  b = a;
  EXPECT_TRUE(a.operator[]("Sasha")==b.operator[]("Sasha"));
}

TEST(HTtest, test_3){
  HashTable a;
  Value v1 = {19, 58};
  Key k1 = "Oksana";
  Value v2 = {22, 67};
  Key k2 = "Olesya";
  Value v3 = {29, 51};
  Key k3 = "Anya";
  Value v4 = {32, 62};
  Key k4 = "Katya";
  Value v5 = {15, 55};
  Key k5 = "Tonya";
  Value v6 = {18, 69};
  Key k6 = "Sasha";
  int res = a.insert(k1,v1);
  res+= a.insert(k2,v2);
  res+= a.insert(k3,v3);
  res+= a.insert(k4,v4);
  res+= a.insert(k5,v5);
  res+= a.insert(k6,v6);
  res+= a.insert(k2,v2);
  //EXPECT_TRUE(v1 == a.operator[]("Oksana") && res == 6);
  HashTable b;
  b = a;
  EXPECT_TRUE(a.operator[]("Oksana")==b.operator[]("Oksana")&& a.operator[]("Katya")==b.operator[]("Katya")&&res == 6);
}

/*TEST(HTtest, test_4){
  HashTable a;
  Value v1 = {19, 58};
  Key k1 = "Oksana";
  Value v2 = {22, 67};
  Key k2 = "Olesya";
  Value v3 = {29, 51};
  Key k3 = "Anya";
  Value v4 = {32, 62};
  Key k4 = "Katya";
  Value v5 = {15, 55};
  Key k5 = "Tonya";
  Value v6 = {18, 69};
  Key k6 = "Sasha";
  Value v7 = {312, 621};
  Key k7 = "Sonya";
  Value v8 = {151, 551};
  Key k8 = "Tanya";
  Value v9 = {181, 619};
  Key k9 = "Diana";
  int res = a.insert(k1,v1);
  res+= a.insert(k2,v2);
  res+= a.insert(k3,v3);
  res+= a.insert(k4,v4);
  res+= a.insert(k5,v5);
  res+= a.insert(k6,v6);
  res+= a.insert(k2,v2);
  HashTable b;
  b = a;
  res-= b.erase(k5);
  res+= a.insert(k7,v7);
  res+= a.insert(k8,v8);
  res+= a.insert(k9,v9);
  res+= a.insert(k5,v5);
  res-= b.erase(k7);
  res-= b.erase(k8);
  res-= b.erase(k9);
  EXPECT_TRUE(a==b);
}*/

int main(int argc, char **argv)
{
  ::testing::InitGoogleTest(&argc, argv);

  return RUN_ALL_TESTS();
}
