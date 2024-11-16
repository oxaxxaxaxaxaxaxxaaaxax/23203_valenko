#include "hashtable.h"
#include "googletest/googletest/include/gtest/gtest.h"


TEST(INSERT_ERASE, test_copy_small){
  HashTable a;
  a.SetTestingMode();
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
  b.SetTestingMode();
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

TEST(INSERT_ERASE, test_copy_small_2){
  HashTable a;
  a.SetTestingMode();
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
  b.SetTestingMode();
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

TEST(INSERT_ERASE, test_empty_key){
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

TEST(EXCEPTION, test_1){
  HashTable a;
  Value v6 = {18, 69};
  EXPECT_TRUE(a.insert("aaa", v6));
  EXPECT_THROW(a.at("aa"), std::runtime_error);
}


TEST(EXCEPTION, test_2){
  HashTable a;
  const Value v6 = {18, 69};
  const Key k6 = "aaa";
  EXPECT_TRUE(a.insert(k6, v6));
  EXPECT_THROW(a.at("aa"), std::runtime_error);
}

TEST(OPERATION, test_1){
  HashTable a;
  a.SetTestingMode();
  for(unsigned int i =0;i<10000;i++){
    Key k = "AAA" + std::to_string(i);
    Value v = {i, i};
    EXPECT_TRUE(a.insert(k,v));
  }
  for(unsigned int i =0;i<9900;i++){
    Key k = "AAA" + std::to_string(i);
    EXPECT_TRUE(a.erase(k));
    EXPECT_FALSE(a.erase(k));
    EXPECT_FALSE(a.erase(k));
  } 
  HashTable b;
  b.SetTestingMode();
  for(unsigned int i =9900;i<10000;i++){
    Key k = "AAA" + std::to_string(i);
    Value v = {i, i};
    b[k] = v;
  }
  EXPECT_TRUE(a==b);
}

TEST(OPERATION, test_move){
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

TEST(OPERATION, test_copy){
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

TEST(INSERT_ERASE, test_move_small){
  HashTable a;
  a.SetTestingMode();
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
  b.SetTestingMode();
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

TEST(INSERT_ERASE, test_clear_small){
  HashTable a;
  a.SetTestingMode();
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
  b.SetTestingMode();
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

TEST(SWAP, test_1){
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

TEST(INSERT_ERASE, test_copy_large_1){
  HashTable a;
  a.SetTestingMode();
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

TEST(INSERT_ERASE, test_copy_large_2){
  HashTable a;
  a.SetTestingMode();
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

TEST(INSERT_ERASE, test_move_large){
  HashTable a;
  a.SetTestingMode();
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

TEST(INSERT_ERASE, test_equal){
  HashTable a;
  a.SetTestingMode();
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
  a.SetTestingMode();
  Key k1 = "AAA9999";
  Value v1 = {9999,9999};
  EXPECT_TRUE(b.insert(k1,v1));
  EXPECT_TRUE(a==b);
}

TEST(INSERT_ERASE, test_not_equal){
  HashTable a;
  a.SetTestingMode();
  for(unsigned int i =0;i<10000;i++){
    Key k = "AAA" + std::to_string(i);
    Value v = {i, i};
    EXPECT_TRUE(a.insert(k,v));
    EXPECT_FALSE(a.insert(k,v));
  }
  for(unsigned int i =0;i<9998;i++){
    Key k = "AAA" + std::to_string(i);
    EXPECT_TRUE(a.erase(k));
    EXPECT_FALSE(a.erase(k));
    EXPECT_FALSE(a.erase(k));
  } 
  HashTable b;
  b.SetTestingMode();
  Key k1 = "AAA9990";
  Value v1 = {9990,9990};
  Key k2 = "AAA9998";
  Value v2 = {9998,9998};
  EXPECT_TRUE(b.insert(k2,v2));
  EXPECT_TRUE(b.insert(k1,v1));
  EXPECT_TRUE(a!=b);
}


TEST(INSERT_ERASE, test_clear){
  HashTable a;
  a.SetTestingMode();
  for(unsigned int i =0;i<10000;i++){
    Key k = "AAA" + std::to_string(i);
    Value v = {i, i};
    EXPECT_TRUE(a.insert(k,v));
    EXPECT_FALSE(a.insert(k,v));
  }
  a.clear();
  EXPECT_TRUE(!a.size());
  for(unsigned int i =0;i<10000;i++){
    Key k = "AAA" + std::to_string(i);
    Value v = {i, i};
    EXPECT_TRUE(a.insert(k,v));
    EXPECT_FALSE(a.insert(k,v));
  }
  a.clear();
  HashTable b;
  b.SetTestingMode();
  EXPECT_TRUE(a==b);
  for(unsigned int i =0;i<10000;i++){
    Key k = "AAA" + std::to_string(i);
    Value v = {i, i};
    EXPECT_TRUE(b.insert(k,v));
    EXPECT_FALSE(b.insert(k,v));
  }
  EXPECT_FALSE(a==b);
  b.clear();
  EXPECT_TRUE(a==b);
}

TEST(INSERT_ERASE, test_is_empty){
  HashTable a;
  a.SetTestingMode();
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
  a = std::move(b);
  EXPECT_TRUE(b.empty());
}

TEST(INSERT_ERASE, test_not_equal_lt){
  HashTable a;
  a.SetTestingMode();
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
  b.SetTestingMode();
  Key k1 = "AAAggA9999";
  Value v1 = {9999,9999};
  EXPECT_TRUE(b.insert(k1,v1));
  EXPECT_TRUE(a!=b);
}

TEST(EXCEPTION, test_3){
  HashTable a;
  a.SetTestingMode();
  for(unsigned int i =0;i<10000;i++){
    Key k = "AAA" + std::to_string(i);
    Value v = {i, i};
    EXPECT_TRUE(a.insert(k,v));
    EXPECT_FALSE(a.insert(k,v));
  }
  EXPECT_THROW(a.at("aa"), std::runtime_error);
  EXPECT_THROW(a.at("AAAaaaAaa"), std::runtime_error);
  EXPECT_THROW(a.at("aAaaaAAaAa"), std::runtime_error);
  EXPECT_THROW(a.at("aAAAAAAAAAAa"), std::runtime_error);
}

TEST(OPERATOR, test_5){
  HashTable a;
  Value v1 = {33, 55};
  Value v2 = {44,77};
  a["hhh"] = v1;
  a["hhh"] = v2;
  EXPECT_TRUE(a.at("hhh") == v2);
}

int main(int argc, char **argv)
{
  ::testing::InitGoogleTest(&argc, argv);

  return RUN_ALL_TESTS();
}