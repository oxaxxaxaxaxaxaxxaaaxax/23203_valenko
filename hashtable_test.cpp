#include "hashtable.cpp"
#include "googletest/googletest/include/gtest/gtest.h"
using namespace std;

TEST(HTtest, test_1) {
  HashTable a;
  Value v;
  v.age=19;
  v.weight = 58;
  a.insert("Oksana", v);
  EXPECT_TRUE(v == a.operator[]("Oksana"));
}

TEST(HTtest, test_2) {
  ASSERT_FALSE('b' == 'b');
  cout << "continue test after failure" << endl;
}

int main(int argc, char **argv)
{
  ::testing::InitGoogleTest(&argc, argv);

  return RUN_ALL_TESTS();
}
