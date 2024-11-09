#include "hashtable.h"

#include <assert.h>
#include <algorithm>
#include <iostream>
#include <string>


namespace {
  constexpr int initial_capacity = 4; 
  constexpr size_t default_factor = 31;
  constexpr int multiplier = 2;
}

struct HashTable::Node{ 
  Key key;
  Value * data;
  bool flag =true;
  Node * next = nullptr;

  Node(): data(new Value){
  }
  ~Node() noexcept{ 
    delete data;
    if(next != nullptr){
      delete next;
    }
  }
  
  Node(const Key &k, const Value& v):key(k),data(new Value){
    data->age = v.age;
    data->weight = v.weight;
  }

  Node(Node &&b) = delete;

  Node(const Node& b){
    const Node *tmp = &b;
    Node * pointer = this;
    data = new Value;
    while(tmp->next != nullptr){
      pointer->key=tmp->key;
      pointer->flag =tmp->flag;
      pointer->data->age=tmp->data->age;
      pointer->data->weight=tmp->data->weight;
      pointer->next = new Node();
      pointer = pointer->next;
      tmp = tmp->next;
    }
    pointer->key=tmp->key;
    pointer->flag =tmp->flag;
    pointer->data->age=tmp->data->age;
    pointer->data->weight=tmp->data->weight;
  }

  Node& operator=(const Node& b){
    if(this == &b) return *this;
    const Node *tmp = &b;
    Node * pointer = this;
    while(tmp->next!= nullptr){
      pointer->key=tmp->key;
      pointer->flag =tmp->flag;
      pointer->data->age=tmp->data->age;
      pointer->data->weight=tmp->data->weight;
      pointer->next = new Node();
      pointer = pointer->next;
      tmp = tmp->next;
    }
    pointer->key=tmp->key;
    pointer->flag =tmp->flag;
    pointer->data->age=tmp->data->age;
    pointer->data->weight=tmp->data->weight;
    return *this;
  }

  Node& operator=(Node&& b) noexcept{
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


HashTable::HashTable():capacity(initial_capacity),
  table(new Node*[initial_capacity]){
  std::fill(&table[0], &table[capacity], nullptr);
}


HashTable::~HashTable() noexcept{
  if(table == nullptr) return;
  for(int i=0; i< capacity;i++){
    delete table[i];
  }
  delete [] table;
}


HashTable::HashTable(size_t size):capacity(size),
  table(new Node*[capacity]){
  std::fill(&table[0], &table[capacity], nullptr);
}


HashTable::HashTable(const HashTable& b):curr_size(b.curr_size),capacity(b.capacity),
  table(new Node*[b.capacity]){
  for (int i=0;i<capacity;i++){
    if(b.table[i] == nullptr) {
      table[i] = nullptr;
      continue;
    }
    table[i] = new Node;
    *(table[i]) = *(b.table[i]);
  }
}


HashTable::HashTable(HashTable&& b) noexcept :curr_size(b.curr_size),capacity(b.capacity),table(b.table){
  b.table = nullptr;
}


void HashTable::swap(HashTable& b) noexcept{
  std::swap(b, *this);
}


HashTable& HashTable::operator=(const HashTable& b){ 
  if(this == &b) return *this;
  Node ** new_table = new Node*[b.capacity];
    for (int i = 0; i < b.capacity; i++) {
      if(b.table[i] == nullptr) {
        new_table[i] = nullptr;
        continue;
      }
      new_table[i] = new Node(*(b.table[i]));
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


HashTable& HashTable::operator=(HashTable&& b) noexcept{
  if(this == &b) return *this;
  if(table != nullptr){
    for(int i=0;i<capacity;i++){
      delete table[i];
    }
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
    if(table[i]== nullptr){
      continue;
    }
    while(table[i]!= nullptr){
      if(!erase(table[i]->key)){ throw std::runtime_error("Key not erase");}
    }
  }
}


bool HashTable::erase(const Key& k){
  if(!curr_size) {
    return false;
  }
  size_t index = hashFunction(k);
  if(table[index] ==nullptr) return false;

  Node * tmp = table[index];
  if(tmp->key == k){
    table[index] = tmp ->next;
    tmp->next = nullptr;
    delete tmp;
    tmp = nullptr;
    curr_size--;
    return true;
  }

  Node * previous = nullptr;
  while(tmp->key != k){
    previous = tmp;
    tmp = tmp->next;
    if(tmp == nullptr) return false;
  }

  Node * pointer = tmp->next;
  tmp->next = nullptr;
  delete tmp;
  tmp = nullptr;
  previous ->next = pointer;
  curr_size--;
  return true;
}
  

bool HashTable::insert(const Key& k, const Value& v){
  if (curr_size >=capacity){
    expandMemoryIfNeeded();
  }
  size_t index = hashFunction(k);
  if(table[index] == nullptr){
    table[index] =new Node(k, v);
    curr_size++;
    return true;
  }
  if(table[index]->key == k) { 
    return false;
  }
  Node * tmp = table[index];
  while((tmp)->next!= nullptr){
    if(tmp->next->key == k) {
      return false;
    }
    tmp = tmp->next;
  }
  tmp->next = new Node(k, v);
  curr_size++;
  return true;
}


bool HashTable::contains(const Key& k) const{ 
  size_t index = hashFunction(k);
  Node * current = table[index];
  while(current != nullptr){
    if(k == current->key) return true;
    current = current->next;
  }
  return false;
 }


Value& HashTable::operator[](const Key& k){
  if(!contains(k)){ 
    if(!insert(k, Value())){
      throw std::runtime_error("Key not insert");
    }
  }
  const size_t index = hashFunction(k);
  Node *a = table[index];
  while(a != nullptr){
    if(a->key == k){
      return *(a->data);
    }
    a = a->next;
  }
  throw std::runtime_error("Key not found after insertion");
}


Value& HashTable::at(const Key& k){
  size_t index = hashFunction(k);
  Node *a = table[index];
  if(a == nullptr){
    throw std::runtime_error("Value is not found");
  }
  while(a->key != k){
    a = a->next;
    if(a == nullptr){
      throw std::runtime_error("Value is not found");
    }
  }
  return *(a->data);
}
  

const Value& HashTable::at(const Key& k) const{
  return const_cast<HashTable&>(*this).at(k);
}


bool operator==(const HashTable& a, const HashTable& b){
  if(a.curr_size != b.curr_size) return false;
  for(int i=0; i<b.capacity;i++){
    if(b.table[i]== nullptr) {
      continue;
    }
    HashTable::Node *current = b.table[i];
    if(b.table[i]->key.empty() && !b.table[i]->flag) continue;
    while(current!= nullptr){
      try{
        if(b.at(current->key) != a.at(current->key)) return false;
      }
      catch(const std::runtime_error&e){
        return false;
      }
      current= current->next;
    }
    
  }
  return true;
}

bool operator!=(const HashTable& a, const HashTable& b){
  return !(a == b);
}


void HashTable::Rehash(HashTable &a) noexcept{
  a.testing_mode = true;
    for(int i=0;i<capacity;i++){
      if(table[i] == nullptr) continue;
      Node * tmp = table[i];
      while(tmp->next != nullptr) {
        a.insert(tmp->key, *tmp->data);
        tmp = tmp->next;
      }
      a.insert(tmp->key, *tmp->data);
    }
    *this = std::move(a);
}

void HashTable::expandMemoryIfNeeded() noexcept{
    HashTable a(capacity * multiplier);
    Rehash(a);
}

size_t HashTable::hashFunction(const Key &key) const noexcept{
  if(testing_mode) {
    return hashFunctionForTest(key);
  }
  size_t bucketIndex =0;
  size_t sum = 0;
  size_t factor = default_factor;
  for (char i : key ){
      sum = ((sum % capacity) + ((static_cast<int>(i)) * factor) % capacity) % capacity;
      factor = ((factor % __INT16_MAX__) * (default_factor % __INT16_MAX__)) % __INT16_MAX__;
  }
  bucketIndex = sum;
  return bucketIndex;
}
