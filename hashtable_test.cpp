//#include "hashtable.h"
#include "hashtable.cpp"
#include "googletest/googletest/include/gtest/gtest.h"


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
  EXPECT_TRUE(a.insert(k1,v1));
  EXPECT_TRUE(a.insert(k2,v2));
  EXPECT_TRUE(a.insert(k3,v3));
  EXPECT_TRUE(a.insert(k4,v4));
  EXPECT_TRUE(a.insert(k5,v5));
  EXPECT_TRUE(a.insert(k6,v6));
  EXPECT_FALSE(a.insert(k2,v2));
  HashTable b;
  b = a;
  EXPECT_TRUE(b.erase(k5));
  EXPECT_FALSE(b.erase(k5));
  EXPECT_TRUE(b.insert(k7,v7));
  EXPECT_TRUE(b.insert(k8,v8));
  EXPECT_TRUE(b.insert(k9,v9));
  EXPECT_TRUE(b.insert(k5,v5));
  EXPECT_TRUE(b.erase(k7));
  EXPECT_TRUE(b.erase(k8));
  EXPECT_TRUE(b.erase(k9));
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
  EXPECT_TRUE(a.insert(k1,v1));
  EXPECT_TRUE(a.insert(k2,v2));
  EXPECT_TRUE(a.insert(k3,v3));
  EXPECT_TRUE(a.insert(k4,v4));
  EXPECT_TRUE(a.insert(k5,v5));
  EXPECT_TRUE(a.insert(k6,v6));
  EXPECT_FALSE(a.insert(k2,v2));
  HashTable b;
  b = a;
  EXPECT_TRUE(b.erase(k5));
  EXPECT_TRUE(b.insert(k7,v7));
  EXPECT_TRUE(b.insert(k8,v8));
  EXPECT_TRUE(b.insert(k9,v9));
  EXPECT_TRUE(b.insert(k5,v5));
  EXPECT_TRUE(b.erase(k7));
  EXPECT_TRUE(b.erase(k8));
  EXPECT_TRUE(b.erase(k9));
  EXPECT_TRUE(!(a!=b));
}

TEST(HTtest, test_8){
  HashTable a;
  Value v1 = {0,0};
  Value v6 = {18, 69};
  EXPECT_TRUE(a.insert("", v1));
  EXPECT_TRUE(a.insert("a", v6 ));
  EXPECT_TRUE(a.erase(""));
  EXPECT_FALSE(a.erase(""));
  a.clear();
  EXPECT_FALSE(a.erase(""));
  EXPECT_TRUE(!a.size());
}

TEST(HTtest, test_9){
  HashTable a;
  Value v6 = {18, 69};
  EXPECT_TRUE(a.insert("aaa", v6));
  EXPECT_THROW(a.at("aa"), std::runtime_error);
}


TEST(HTtest, test_10){
  HashTable a;
  const Value v6 = {18, 69};
  const Key k6 = "aaa";
  EXPECT_TRUE(a.insert(k6, v6));
  EXPECT_THROW(a.at("aa"), std::runtime_error);
}

TEST(HTtest, test_11){
  HashTable a;
  Value v6 = {18, 69};
  Value v5 = {16,77};
  EXPECT_TRUE(a.insert("aaa", v6));
  HashTable b;
  b=a;
  b["aaa"] = v5;
  EXPECT_TRUE(b["aaa"].age == 16);
}

TEST(HTtest, test_12){
  HashTable a;
  Value v6 = {18, 69};
  Value v5 = {16,77};
  EXPECT_TRUE(a.insert("aaa", v6));
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
  EXPECT_TRUE(a.insert("aaa", v6));
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
  EXPECT_TRUE(a.insert(k1,v1));
  EXPECT_TRUE(a.insert(k2,v2));
  EXPECT_TRUE(a.insert(k3,v3));
  EXPECT_TRUE(a.insert(k4,v4));
  EXPECT_TRUE(a.insert(k5,v5));
  EXPECT_TRUE(a.insert(k6,v6));
  EXPECT_FALSE(a.insert(k2,v2));
  HashTable b;
  b = std::move(a);
  EXPECT_TRUE(b.erase(k5));
  EXPECT_TRUE(b.insert(k7,v7));
  EXPECT_TRUE(b.insert(k8,v8));
  EXPECT_TRUE(b.insert(k9,v9));
  EXPECT_TRUE(b.insert(k5,v5));
  EXPECT_TRUE(b.erase(k7));
  EXPECT_TRUE(b.erase(k8));
  EXPECT_TRUE(b.erase(k9));
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
  EXPECT_TRUE(a.insert(k1,v1));
  EXPECT_TRUE(a.insert(k2,v2));
  EXPECT_TRUE(a.insert(k3,v3));
  EXPECT_TRUE(a.insert(k4,v4));
  EXPECT_TRUE(a.insert(k5,v5));
  EXPECT_TRUE(a.insert(k6,v6));
  EXPECT_FALSE(a.insert(k2,v2));
  HashTable b;
  EXPECT_TRUE(b.insert(k7,v7));
  EXPECT_TRUE(b.insert(k8,v8));
  EXPECT_TRUE(b.insert(k9,v9));
  EXPECT_TRUE(b.insert(k5,v5));
  EXPECT_TRUE(b.erase(k7));
  EXPECT_TRUE(b.erase(k8));
  EXPECT_TRUE(b.erase(k9));
  b.clear();
  EXPECT_TRUE(b.empty());
}

TEST(HTtest, test_16){
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
  EXPECT_TRUE(a.insert(k1,v1));
  EXPECT_TRUE(a.insert(k2,v2));
  EXPECT_TRUE(a.insert(k3,v3));
  EXPECT_TRUE(a.insert(k4,v4));
  EXPECT_TRUE(a.insert(k5,v5));
  EXPECT_TRUE(a.insert(k6,v6));
  HashTable c;
  a.swap(c);
  EXPECT_TRUE(a.empty());
}

TEST(HTtest, test_17){
  HashTable a;
  for(unsigned int i =0;i<100;i++){
    Key k = "AAA" + std::to_string(i);
    Value v = {i, i};
    EXPECT_TRUE(a.insert(k,v));
    EXPECT_FALSE(a.insert(k,v));
  }
  for(unsigned int i =0;i<100;i++){
    Key k = "AAA" + std::to_string(i);
    EXPECT_TRUE(a.erase(k));
    EXPECT_FALSE(a.erase(k));
    EXPECT_FALSE(a.erase(k));
  } 
  HashTable b = a;
  b = a;
  EXPECT_TRUE(b.empty());
}

TEST(HTtest, test_18){
  HashTable a;
  for(unsigned int i =0;i<10000;i++){
    Key k = "AAA" + std::to_string(i);
    Value v = {i, i};
    EXPECT_TRUE(a.insert(k,v));
  }
  for(unsigned int i =0;i<10000;i++){
    Key k = "AAA" + std::to_string(i);
    EXPECT_TRUE(a.erase(k));
    EXPECT_FALSE(a.erase(k));
    EXPECT_FALSE(a.erase(k));
  } 
  HashTable b(a);
  a = b;
  b = a;
  EXPECT_TRUE(b.empty());
}

TEST(HTtest, test_19){
  HashTable a;
  for(unsigned int i =0;i<10000;i++){
    Key k = "AAA" + std::to_string(i);
    Value v = {i, i};
    EXPECT_TRUE(a.insert(k,v));
    EXPECT_FALSE(a.insert(k,v));
  }
  for(unsigned int i =0;i<10000;i++){
    Key k = "AAA" + std::to_string(i);
    EXPECT_TRUE(a.erase(k));
    EXPECT_FALSE(a.erase(k));
    EXPECT_FALSE(a.erase(k));
  } 
  HashTable b(std::move(a));
  b = b;
  EXPECT_TRUE(b.empty());
}

TEST(HTtest, test_20){
  HashTable a;
  for(unsigned int i =0;i<10000;i++){
    Key k = "AAA" + std::to_string(i);
    Value v = {i, i};
    EXPECT_TRUE(a.insert(k,v));
    EXPECT_FALSE(a.insert(k,v));
  }
  for(unsigned int i =0;i<9999;i++){
    Key k = "AAA" + std::to_string(i);
    EXPECT_TRUE(a.erase(k));
    EXPECT_FALSE(a.erase(k));
    EXPECT_FALSE(a.erase(k));
  } 
  HashTable b;
  Key k1 = "AAA9999";
  Value v1 = {9999,9999};
  EXPECT_TRUE(b.insert(k1,v1));
  EXPECT_TRUE(a==b);
}

TEST(HTtest, test_21){
  HashTable a;
  for(unsigned int i =0;i<10000;i++){
    Key k = "AAA" + std::to_string(i);
    Value v = {i, i};
    EXPECT_TRUE(a.insert(k,v));
    EXPECT_FALSE(a.insert(k,v));
  }
  for(unsigned int i =0;i<9999;i++){
    Key k = "AAA" + std::to_string(i);
    EXPECT_TRUE(a.erase(k));
    EXPECT_FALSE(a.erase(k));
    EXPECT_FALSE(a.erase(k));
  } 
  HashTable b;
  Key k1 = "AAA9999";
  Value v1 = {9999,9999};
  EXPECT_TRUE(b.insert(k1,v1));
  EXPECT_FALSE(a!=b);
}


TEST(HTtest, test_1) {
  HashTable a;
  Value v = {19,58};
  EXPECT_TRUE(a.insert("Oksana", v));
  EXPECT_TRUE(v == a.operator[]("Oksana"));
}

TEST(HTtest, test_2) {
  HashTable a;
  Value v1 = {19, 58};
  Value v2 = {22, 67};
  EXPECT_TRUE(a.insert("Oksana", v1));
  EXPECT_FALSE(a.insert("Oksana", v2));
  EXPECT_TRUE(v1 == a.operator[]("Oksana"));
}

TEST(HTtest, test_3) {
  HashTable a;
  Value v1 = {19, 58};
  Value v2 = {22, 67};
  EXPECT_TRUE(a.insert("Oksana", v1));
  EXPECT_TRUE(a.insert("Olesya", v2));
  a.clear();
  EXPECT_TRUE(a.operator[]("Oksana")== a.operator[]("Olesya"));
}

TEST(HTtest, test_4){
  HashTable a;
  Value v5 = {15, 55};
  Key k5 = "Tonya";
  Value v6 = {18, 69};
  Key k6 = "Sasha";
  EXPECT_TRUE(a.insert(k5,v5));
  EXPECT_TRUE(a.insert(k6,v6));
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
  EXPECT_TRUE(a.insert(k1,v1));
  EXPECT_TRUE(a.insert(k2,v2));
  EXPECT_TRUE(a.insert(k3,v3));
  EXPECT_TRUE(a.insert(k4,v4));
  EXPECT_TRUE(a.insert(k5,v5));
  EXPECT_TRUE(a.insert(k6,v6));
  EXPECT_FALSE(a.insert(k2,v2));
  HashTable b;
  b = a;
  EXPECT_TRUE(a.operator[]("Oksana")==b.operator[]("Oksana")&& a.operator[]("Katya")==b.operator[]("Katya"));
}


int main(int argc, char **argv)
{
  ::testing::InitGoogleTest(&argc, argv);

  return RUN_ALL_TESTS();
}
