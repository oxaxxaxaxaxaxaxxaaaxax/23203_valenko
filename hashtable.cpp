
#include <cstring>
#include <iostream>
#include <utility>


typedef std::string Key;
static constexpr int initial_capacity = 4;

struct Value {
  unsigned int age =0;
  unsigned int weight = 0;

  //Value():age(0),weight(0){}


  Value& operator=(const Value& b){
    age = b.age;
    weight = b.weight;
    return *this;
  }
  friend bool operator!=(const Value& a, const Value& b){
    if(a.age != b.age) return 1;
    if(a.weight != b.weight) return 1;
    return 0;
  }
  friend bool operator==(const Value& a, const Value& b){
    if(a.age != b.age) return 0;
    if(a.weight != b.weight) return 0;
    return 1;
  }
};

struct Node{
  Key key = "";
  Value * data;
  bool flag =false;
  Node * next = nullptr;
  Node(): data(new Value){}
  ~Node(){
    if(data != nullptr){
      delete data;
    }
    if(next != nullptr){
      delete next;
    }
  }
  Node(const Key &k, const Value& v):key(k),data(new Value),flag(true){
    data->age = v.age;
    data->weight = v.weight;
    next = nullptr;
  }
  Node(const Node& b):key(b.key),flag (b.flag){
    data->age=b.data->age;
    data->weight=b.data->weight;
  }
  Node& operator=(const Node& b){
    if(this == &b) return *this;
    key=b.key;
    flag =b.flag;
    data->age=b.data->age;
    data->weight=b.data->weight;
    return *this;
  }

  Node& operator=(Node&& b){
    if(this == &b) return *this;
    key=b.key;
    flag =b.flag;
    data->age=b.data->age;
    data->weight=b.data->weight;
    next = nullptr;
    b.next= nullptr;
    b.data = nullptr;
    return *this;
  }
};

class HashTable
{
public:
  HashTable():capacity(initial_capacity){
    table = new Node*[initial_capacity];
    for(int i=0; i< capacity;i++){
      table[i] = new Node;
    }
  }

   ~HashTable(){
    if(table == nullptr) return;
    for(int i=0; i< capacity;i++){
      delete table[i];
    }
    delete [] table;
  }

  HashTable(int size){
    capacity=size;
    curr_size=0;
    table = new Node*[capacity];
    for(int i=0; i< capacity;i++){
      table[i] = new Node;
    }
  }

  HashTable(const HashTable& b):curr_size(b.curr_size),capacity(b.capacity){
    table = new Node*[capacity];
    for (int i=0;i<capacity;i++){
      table[i] = new Node;
      std::copy(b.table[i], b.table[i]+1, table[i]);
    }
  }

  HashTable(HashTable&& b):curr_size(b.curr_size),capacity(b.capacity){
    //table = new Node*[capacity];
    table = b.table;
    b.table = nullptr;
  }

  // Обменивает значения двух хэш-таблиц.
  // Подумайте, зачем нужен этот метод, при наличии стандартной функции
  // std::swap.
  void swap(HashTable& b){
    HashTable c(std::move(b));
    b= std::move(*this);
    *this = std::move(c);
  }

  HashTable& operator=(const HashTable& b){ 
    if(this == &b) return *this;
    Node ** new_table = new Node*[b.capacity];
    for (int i = 0; i < b.capacity; i++) {
      new_table[i] = new Node;
    }
    for(int i=0;i<b.capacity;i++){
      std::copy(b.table[i], b.table[i]+1, new_table[i]);
    }

    for (int i = 0; i < capacity; i++) {
      delete table[i];
    }
    delete [] table;
    table = new_table;
    curr_size = b.curr_size;
    capacity = b.capacity;
    return *this;
  }

  HashTable& operator=(HashTable&& b){
    if(this == &b) return *this;
    //Node ** new_table = new Node*[b.capacity];
    /*for (int i=0;i<b.capacity;i++){
      new_table[i] = std::move(b.table[i]);
    }*/
   for(int i=0;i<capacity;i++){
      //if(table[i] == nullptr) continue;
      //std::cout << table[i] << std::endl;
      delete table[i];
    }
    delete [] table;
    table = b.table;
    b.table = nullptr;

    curr_size = b.curr_size;
    capacity = b.capacity;
    //b.curr_size=0;
    //b.capacity =0;
    //delete [] b.table;
    //table = new_table;
    return *this;
  }


  // Очищает контейнер.
  void clear(){
    for(int i =0; i< capacity;i++){
      if(table[i]->flag == 0){
        continue;
      }
      while(table[i]->next!= nullptr){
        table[i] = new Node(); //new?
        //table[i]->data->age=0;
        //table[i]->data->weight=0;
        //table[i]->key ="";
        table[i]=table[i]->next;
      }
      table[i] = new Node();
      //table[i]->data->age=0;
      //table[i]->data->weight=0;
      //table[i]->key ="";
    }
    curr_size=0;
  }

  // Удаляет элемент по заданному ключу.
  bool erase(const Key& k){
    if(!curr_size) {
      return 0;
    }
    size_t index = hashFunction(k);
    while((table[index]->key != k)||((table[index]->flag ==0)&&(k == ""))){
      table[index] = table[index]->next;
      if(table[index] == nullptr) return 0;
    }
    Node * pointer = table[index]->next;
    table[index] = std::move(new Node());
    table[index]->next = pointer;

    curr_size--;
    //(table[index])->data->age = 0;
    //(table[index])->data->age = 0;
    //table[index]->key = "";
    return 1;
  }
  // Вставка в контейнер. Возвращаемое значение - успешность вставки.
  bool insert(const Key& k, const Value& v){

    curr_size++;
    if (curr_size>capacity){
      expandMemoryIfNeeded();
    }
    size_t index = hashFunction(k);
    if(table[index]->flag == 0){
      table[index] =new Node(k, v);
      //table[index]->next = new Node;
      return 1;
    }
    if(table[index]->key == k) {
      curr_size--;
      return 0;
    }
    while((table[index])->next!= nullptr){
      if(table[index]->next->key == k) return 0;
      table[index] = table[index]->next;
    }
    table[index]->next = new Node(k, v);
    //table[index]->next = new Node;
    return 1;
  }

  // Проверка наличия значения по заданному ключу.
  bool contains(const Key& k) const{ 
    size_t index = hashFunction(k);
    if(k == table[index]->key) return 1;
    while(table[index]->next != nullptr){
      if(table[index]->next->flag == 0) break;
      if(k == table[index]->next->key) return 1;
      table[index] = table[index]->next;
    }
    return 0;
  }

  // Возвращает значение по ключу. Небезопасный метод.
  Value& operator[](const Key& k){
    if(!contains(k)){ 
      int res = insert(k, Value());
      //table[index] = new Node;
      //table[index]->key = k;
      return this->operator[](k);
    }
    size_t index = hashFunction(k);
    Node *a = table[index];
    while(a->key != k){
      a = a->next;
    }
    return *(a->data);
  }

  // Возвращает значение по ключу. Бросает исключение при неудаче.
  Value& at(const Key& k){
    size_t index = hashFunction(k);
      while(table[index]->key != k){
        if(table[index]->flag == 0){
          throw std::runtime_error("Value is not found");
        }
        table[index] = (table[index])->next;
      }
    return *((table[index])->data);
  }
  const Value& at(const Key& k) const{
    size_t index = hashFunction(k);
      while(table[index]->key != k){
        if(table[index]->flag == 0){
          throw std::runtime_error("Value is not found");
        }
        table[index] = (table[index])->next;
      }
    const Value& temp = *((table[index])->data);
    return temp;
  }

  size_t size() const{
    return curr_size;
  }
  bool empty() const{
    if(!curr_size) return 1;
    return 0;
  }

  friend bool operator==(const HashTable& a, const HashTable& b){
    if(a.curr_size != b.curr_size) return 0;
    for(int i=0; i<a.capacity;i++){
      if(b.at(b.table[i]->key) != a.at(b.table[i]->key)) return 0;
    }
    return 1;
  }
  friend bool operator!=(const HashTable& a, const HashTable& b){
    if(a.curr_size != b.curr_size) return 1;
    for(int i=0; i<a.capacity;i++){
      if(b.at(b.table[i]->key) != a.at(b.table[i]->key)) return 1;
    }
    return 0;
  }
  private:
    size_t curr_size =0;
    size_t capacity=0;
    Node ** table;


  void Rehash(HashTable &a){
    for(int i=0;i<capacity;i++){
      if(!(table[i]->flag)) continue;
      while(table[i]->next!= nullptr){
        a.insert(table[i]->key, (*(table[i]->data)));
        table[i] = table[i]->next;
      }
      a.insert(table[i]->key, (*(table[i]->data)));
    }
    for(int i=0;i<a.capacity;i++){
    }
    *this = std::move(a);


    /*Node ** tmp_table = new Node*[capacity];
    for(int i=0;i<capacity;i++){
      tmp_table[i]= new Node;
    }
    for(int i=0;i<capacity;i++){
      if(new_table[i]->key == "") continue;
      int index = hashFunction(new_table[i]->key);
      if(tmp_table[index]->flag ==0){
        tmp_table[index] = new_table[i];
      }
      else{
        while(tmp_table[index]->next!= nullptr){
          tmp_table[index] = tmp_table[index]->next;
        }
        tmp_table[index]->next = new_table[i];
      }
    }
    table = std::move(tmp_table);
    delete [] new_table;*/


  }

  void expandMemoryIfNeeded(){
    HashTable a(capacity*2);
    for(int i=0;i<a.capacity;i++){
    }
    //a.capacity *=2;
    Rehash(a);
    /*int new_capacity = capacity*2;
    Node ** new_table = new Node*[new_capacity];
    for(int i = 0;i< new_capacity;i++){
      new_table[i] = new Node;
    }
    for(int i=0;i<capacity;i++){
      std::copy(table[i], table[i]+1, new_table[i]);
      //std::memcpy (new_table[i], table[i], sizeof(Node));
      //delete table[i];
    }
    for(int i=0;i<capacity;i++){
      delete table[i];
    }
    delete [] table;
    capacity = new_capacity;
    //table = new_table;
      //table = new_table;
    Rehash(new_table);*/
  }

  size_t hashFunction(const Key &key) const {
    size_t bucketIndex;
    size_t sum = 0;
    size_t factor = 31;
    for (size_t i = 0; i < key.length(); i++) {
        // sum = sum + (ascii value of
        // char * (primeNumber ^ x))...
        // where x = 1, 2, 3....n
        sum = ((sum % capacity) + (((int)key[i]) * factor) % capacity) % capacity;
        // factor = factor * prime
        // number....(prime
        // number) ^ x
        factor = ((factor % __INT16_MAX__) * (31 % __INT16_MAX__)) % __INT16_MAX__;
    }
    bucketIndex = sum;
    return bucketIndex;
  }
};




/*int main(void){
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
  std::cout << b["FIT"].age << std::endl;
  //EXPECT_THROW(a.at("aa"), std::runtime_error);
}*/