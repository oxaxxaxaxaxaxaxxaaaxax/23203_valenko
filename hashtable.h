#pragma once
#include <string>
#include <iostream>


typedef std::string Key;

struct Value {
  unsigned int age =0;
  unsigned int weight = 0;

  Value& operator=(const Value& b);

  friend bool operator!=(const Value& a, const Value& b);

  friend bool operator==(const Value& a, const Value& b);
};

struct Node;

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

  //Assign hashtable by copy.
  HashTable& operator=(const HashTable& b);

  //Assign hashtable by moving.
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
    if(!curr_size) return 1;
    return 0;
  }

  //Return true if equal.
  friend bool operator==(const HashTable& a, const HashTable& b);
  
  //Return true if not equal.
  friend bool operator!=(const HashTable& a, const HashTable& b);

private:
  size_t curr_size =0;
  size_t capacity=0;
  Node ** table;

  //Rehashing the table by value.
  void Rehash(HashTable &a);

  //Extention the table.
  void expandMemoryIfNeeded();

  //Get an index for hashtanle element.
  size_t hashFunction(const Key &key) const ;

};
