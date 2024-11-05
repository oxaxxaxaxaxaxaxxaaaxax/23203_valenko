#pragma once
#include <string>


using Key = std::string;

struct Value {

  unsigned int age = 0;
  unsigned int weight = 0;

  friend bool operator!=(const Value& a, const Value& b){
    if(a.age != b.age) return true;
    if(a.weight != b.weight) return true;
    return false;
  }
  friend bool operator==(const Value& a, const Value& b){
    if(a.age != b.age) return false;
    if(a.weight != b.weight) return false;
    return true;
  }
};


class HashTable
{
public:
  //Hashtable constructor.
  HashTable();

  //Hashtable destructor.
  ~HashTable() noexcept;

  //Hashtable constructor by size.
  explicit HashTable(size_t size);

  //Hashtable copy constructor 
  HashTable(const HashTable& b);

  //Hashtable move constructor.
  HashTable(HashTable&& b) noexcept;

  //Swap two table
  void swap(HashTable& b) noexcept;

  //Assign Hashtable by copy.
  HashTable& operator=(const HashTable& b);

  //Assign Hashtable by moving.
  HashTable& operator=(HashTable&& b) noexcept;

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
  struct Node;
  Node ** table= nullptr;

  //Rehashing the table by value.
  void Rehash(HashTable &a) noexcept;

  //Extention the table.
  void expandMemoryIfNeeded() noexcept;

  //Get an index for hashtanle element.
  size_t hashFunction(const Key &key) const noexcept;


};
