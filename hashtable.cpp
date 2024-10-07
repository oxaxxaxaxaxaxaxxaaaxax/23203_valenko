#include "hashtable.h"
#include <string>
#include <iostream>
#include <utility>


typedef std::string Key;
static constexpr int initial_capacity = 4;

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


HashTable::HashTable():capacity(initial_capacity){
    table = new Node*[initial_capacity];
    for(int i=0; i< capacity;i++){
      table[i] = new Node;
    }
  }

HashTable::~HashTable(){
    if(table == nullptr) return;
    for(int i=0; i< capacity;i++){
      delete table[i];
    }
    delete [] table;
  }

HashTable::HashTable(int size){
    capacity=size;
    curr_size=0;
    table = new Node*[capacity];
    for(int i=0; i< capacity;i++){
      table[i] = new Node;
    }
  }

HashTable::HashTable(const HashTable& b):curr_size(b.curr_size),capacity(b.capacity){
    table = new Node*[capacity];
    for (int i=0;i<capacity;i++){
      table[i] = new Node;
      std::copy(b.table[i], b.table[i]+1, table[i]);
    }
  }

HashTable::HashTable(HashTable&& b):curr_size(b.curr_size),capacity(b.capacity){
    table = b.table;
    b.table = nullptr;
  }


void HashTable::swap(HashTable& b){
    HashTable c(std::move(b));
    b= std::move(*this);
    *this = std::move(c);
  }

HashTable& HashTable::operator=(const HashTable& b){ 
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

HashTable& HashTable::operator=(HashTable&& b){
    if(this == &b) return *this;
   for(int i=0;i<capacity;i++){
      delete table[i];
    }
    delete [] table;
    table = b.table;
    b.table = nullptr;
    curr_size = b.curr_size;
    capacity = b.capacity;
    return *this;
  }



void HashTable::clear(){
    for(int i =0; i< capacity;i++){
      if(table[i]->flag == 0){
        continue;
      }
      while(table[i]->next!= nullptr){
        table[i] = new Node(); 
        table[i]=table[i]->next;
      }
      table[i] = new Node();
    }
    curr_size=0;
  }

bool HashTable::erase(const Key& k){
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
    return 1;
  }
  

bool HashTable::insert(const Key& k, const Value& v){
    curr_size++;
    if (curr_size>capacity){
      expandMemoryIfNeeded();
    }
    size_t index = hashFunction(k);
    if(table[index]->flag == 0){
      table[index] =new Node(k, v);
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
    return 1;
  }


bool HashTable::contains(const Key& k) const{ 
    size_t index = hashFunction(k);
    if(k == table[index]->key) return 1;
    while(table[index]->next != nullptr){
      if(table[index]->next->flag == 0) break;
      if(k == table[index]->next->key) return 1;
      table[index] = table[index]->next;
    }
    return 0;
 }


Value& HashTable::operator[](const Key& k){
    if(!contains(k)){ 
      int res = insert(k, Value());
      return this->operator[](k);
    }
    size_t index = hashFunction(k);
    Node *a = table[index];
    while(a->key != k){
      a = a->next;
    }
    return *(a->data);
}


Value& HashTable::at(const Key& k){
    size_t index = hashFunction(k);
      while(table[index]->key != k){
        if(table[index]->flag == 0){
          throw std::runtime_error("Value is not found");
        }
        table[index] = (table[index])->next;
      }
    return *((table[index])->data);
}
  const Value& HashTable::at(const Key& k) const{
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


bool operator==(const HashTable& a, const HashTable& b){
  if(a.curr_size != b.curr_size) return 0;
  for(int i=0; i<a.capacity;i++){
    if(b.at(b.table[i]->key) != a.at(b.table[i]->key)) return 0;
  }
  return 1;
}

bool operator!=(const HashTable& a, const HashTable& b){
  if(a.curr_size != b.curr_size) return 1;
  for(int i=0; i<a.capacity;i++){
    if(b.at(b.table[i]->key) != a.at(b.table[i]->key)) return 1;
  }
  return 0;
}


void HashTable::Rehash(HashTable &a){
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
}

void HashTable::expandMemoryIfNeeded(){
    HashTable a(capacity*2);
    for(int i=0;i<a.capacity;i++){
    }
    Rehash(a);
  }

size_t HashTable::hashFunction(const Key &key) const {
    size_t bucketIndex;
    size_t sum = 0;
    size_t factor = 31;
    for (size_t i = 0; i < key.length(); i++) {
        sum = ((sum % capacity) + (((int)key[i]) * factor) % capacity) % capacity;
        factor = ((factor % __INT16_MAX__) * (31 % __INT16_MAX__)) % __INT16_MAX__;
    }
    bucketIndex = sum;
    return bucketIndex;
  }




/*int main(void){
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
  HashTable c;
  a.swap(c);
}*/