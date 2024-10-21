#pragma once
#include <string>


typedef std::string Key;

struct Value {

  unsigned int age = 0;
  unsigned int weight = 0;

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
    bool flag =true;
    Node * next = nullptr;

    Node(): data(new Value){}
    ~Node(){ 
      delete data;
      if(next != nullptr){
        delete next;
      }
    }
  
    Node(const Key &k, const Value& v):key(k),data(new Value){
      data->age = v.age;
      data->weight = v.weight;
      next = nullptr;
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
  //Hashtable constructor.
  HashTable();

  //Hashtable destructor.
  ~HashTable();

  //Hashtable constructor by size.
  HashTable(int size);

  //Hashtable copy constructor 
  HashTable(const HashTable& b);

  //Hashtable move constructor.
  HashTable(HashTable&& b);

  //Swap two table
  void swap(HashTable& b);

  //Assign Hashtable by copy.
  HashTable& operator=(const HashTable& b);

  //Assign Hashtable by moving.
  HashTable& operator=(HashTable&& b);

  //Clear the container.
  void clear();

  //Delete an element by key.
  bool erase(const Key& k);

  //Insert into the container. Returned value - succesful insert.
  bool insert(const Key& k, const Value& v);

  //Checking availability value by key.
  bool contains(const Key& k) const;

  //Returning the value by a key. Unsafe method.
  Value& operator[](const Key& k);

  //Returning the value by a key. Throw a exception if it is failure.
  Value& at(const Key& k);

  //Returning the value by a key. Throw a exception if it is failure.
  const Value& at(const Key& k) const;

  //Return size.
  size_t size() const{
    return curr_size;
  }

  //Return true if is empty.
  bool empty() const{
    return (!curr_size);
  }

  //Return true if equal.
  friend bool operator==(const HashTable& a, const HashTable& b);
  
  //Return true if not equal.
  friend bool operator!=(const HashTable& a, const HashTable& b);

  

private:
  size_t curr_size =0;
  size_t capacity=0;
  //struct Node;
  Node ** table= nullptr;

  //Rehashing the table by value.
  void Rehash(HashTable &a);

  //Extention the table.
  void expandMemoryIfNeeded();

  //Get an index for hashtanle element.
  size_t hashFunction(const Key &key) const ;

  //Node* GetNull();

};
