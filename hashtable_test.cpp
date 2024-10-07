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

TEST(HTtest, test_3) {
  HashTable a;
  Value v1 = {19, 58};
  Value v2 = {22, 67};
  int res_1 = a.insert("Oksana", v1);
  int res_2 = a.insert("Olesya", v2);
  a.clear();
  EXPECT_TRUE(a.operator[]("Oksana")== a.operator[]("Olesya"));
}

TEST(HTtest, test_4){
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

TEST(HTtest, test_5){
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
  HashTable b;
  b = a;
  EXPECT_TRUE(a.operator[]("Oksana")==b.operator[]("Oksana")&& a.operator[]("Katya")==b.operator[]("Katya")&&res == 6);
}

TEST(HTtest, test_6){
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
  res+= b.insert(k7,v7);
  res+= b.insert(k8,v8);
  res+= b.insert(k9,v9);
  res+= b.insert(k5,v5);
  res-= b.erase(k7);
  res-= b.erase(k8);
  res-= b.erase(k9);
  EXPECT_TRUE(a==b);
}

TEST(HTtest, test_7){
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
  res+= b.insert(k7,v7);
  res+= b.insert(k8,v8);
  res+= b.insert(k9,v9);
  res-= b.erase(k7);
  res-= b.erase(k8);
  res-= b.erase(k9);
  EXPECT_TRUE(a!=b);
}

TEST(HTtest, test_8){
  HashTable a;
  Value v1 = {0,0};
  Value v6 = {18, 69};
  int res = a.insert("", v1);
  res += a.insert("a", v6 );
  res -= a.erase("");
  res -= a.erase("");
  int size = a.size();
  EXPECT_TRUE(size == 1);
}

TEST(HTtest, test_9){
  HashTable a;
  Value v6 = {18, 69};
  int res = a.insert("aaa", v6);
  try {
    a.at("aa");
    FAIL() << "Ожидаемое исключение не было брошено";
  } catch (const std::runtime_error& e) {
    EXPECT_STREQ("Value is not found", e.what());
  }
}


TEST(HTtest, test_10){
  HashTable a;
  const Value v6 = {18, 69};
  const Key k6 = "aaa";
  int res = a.insert(k6, v6);
  EXPECT_THROW(a.at("aa"), std::runtime_error);
}

TEST(HTtest, test_11){
  HashTable a;
  Value v6 = {18, 69};
  Value v5 = {16,77};
  a.insert("aaa", v6);
  HashTable b;
  b=a;
  b["aaa"] = v5;
  EXPECT_TRUE(b["aaa"].age == 16);
}

TEST(HTtest, test_12){
  HashTable a;
  Value v6 = {18, 69};
  Value v5 = {16,77};
  a.insert("aaa", v6);
  HashTable b(std::move(a));
  Value v7 = {44,79};
  Value v8 = {32,46};
  Value v9 = {13,100};
  b["FIT"] = v7;
  b["aaa"] = v5;
  EXPECT_TRUE(b["FIT"] == v7);
}

TEST(HTtest, test_13){
  HashTable a;
  Value v6 = {18, 69};
  Value v5 = {16,77};
  a.insert("aaa", v6);
  HashTable b(a);
  Value v7 = {44,79};
  Value v8 = {32,46};
  Value v9 = {13,100};
  b["FIT"] = v7;
  b["aaa"] = v5;
  EXPECT_TRUE(b["FIT"] == v7);
}

TEST(HTtest, test_14){
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
  b = std::move(a);
  res-= b.erase(k5);
  res+= b.insert(k7,v7);
  res+= b.insert(k8,v8);
  res+= b.insert(k9,v9);
  res+= b.insert(k5,v5);
  res-= b.erase(k7);
  res-= b.erase(k8);
  res-= b.erase(k9);
  EXPECT_TRUE(b["Tonya"] == v5);
}

TEST(HTtest, test_15){
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
  b = std::move(a);
  res-= b.erase(k5);
  res+= b.insert(k7,v7);
  res+= b.insert(k8,v8);
  res+= b.insert(k9,v9);
  res+= b.insert(k5,v5);
  res-= b.erase(k7);
  res-= b.erase(k8);
  res-= b.erase(k9);
  b.clear();
  EXPECT_TRUE(b.empty());
}

int main(int argc, char **argv)
{
  ::testing::InitGoogleTest(&argc, argv);

  return RUN_ALL_TESTS();
}
