// #include "googletest/googlemock/include/gmock/gmock.h"
// #include "googletest/googletest/include/gtest/gtest.h"
#include <gmock/gmock.h>
#include <gtest/gtest.h>

#include <memory>

//#include "hand_interface.h"
#include "strategy.h"
#include "user_interface.h"
#include "player.h"
#include "card.h"


// class Hand_Test: public Hand_Interface{
//   public:
//     ~Hand_Test() override = default;
//     MOCK_METHOD(void, Show, (), (const, override));
//     MOCK_METHOD(void, HitCard, (Card card), (override));
//     MOCK_METHOD(int, GetTotalSum, (), (const, override));
//     MOCK_METHOD(void, AddToSum, (const int& value),(override));
//     MOCK_METHOD(void, CheckBust, (),(override));
//     MOCK_METHOD(void, CheckVictory, (),(override));
//     MOCK_METHOD(void, UpdateSum,(),(override));
//     MOCK_METHOD(Card&, ShowLastCard, (),(override));
//     MOCK_METHOD(size_t, GetFirstCard,(), (const, override));
//     MOCK_METHOD(std::vector<Card>&,  ReturnCards, (), (override));
//     MOCK_METHOD(void, FreeHand,(), (override));
//     MOCK_METHOD(bool, GetBustMode,(),(const, override));
//     MOCK_METHOD(bool, GetVicMode,() ,(const, override));
// };

class Test_Interface: public Strategy{
  public:
    ~Test_Interface() override = default;

    // MOCK_METHOD(void, ShowWiner, (Player&), (override));
    // MOCK_METHOD(void, ShowMethod, (bool&), (override));
    // MOCK_METHOD(void, ShowCardScore, (int&), (override));
    // MOCK_METHOD(void, ShowScore, (int&), (override));
    // MOCK_METHOD(void, ShowRound, (size_t&), (override));

    MOCK_METHOD(bool, hit, (Card, int), (override));
    MOCK_METHOD(bool, GetStandMode, (), (override));
    MOCK_METHOD(void, End, (), (override));

};

// class Player_Test: public Player_Interface{
//   public:
//     ~Player_Test() = default;
//     MOCK_METHOD(bool, PlayerHit,(Card card), (override));
//     MOCK_METHOD(size_t, GetNumber,(), (const,override));
//     MOCK_METHOD(Hand&, GetHand,(), (override));
//     MOCK_METHOD(void, ShowHand,(), (const,override));
// };
//class Player_Test: public Player_Interface{
//   public:
//     ~Player_Test() = default;
//     MOCK_METHOD(bool, PlayerHit,(Card card), (override));
//     MOCK_METHOD(size_t, GetNumber,(), (const,override));
//     MOCK_METHOD(Hand&, GetHand,(), (override));
//     MOCK_METHOD(void, ShowHand,(), (const,override));
// };
// Тест для метода End
TEST(BlackJack, test_1) {
    Test_Interface test;

    EXPECT_CALL(test, End());
    EXPECT_CALL(test, GetStandMode());
    EXPECT_CALL(test, hit(Card(10), testing::_));

    test.End();
    test.GetStandMode();
    test.hit(Card(10), 20);
}

// TEST(BlackJack, test_2) {
//   Test_Interface* test;
//   EXPECT_CALL(*test, hit(Card(10),testing::_));

//   const size_t number =1;
  
//   std::unique_ptr<Strategy> strategy(test);
//   Player player(std::move(strategy), number);
//   player.PlayerHit(Card(10));
// }

int main(int argc, char **argv) {
  ::testing::InitGoogleMock(&argc, argv);
  return RUN_ALL_TESTS();
}
