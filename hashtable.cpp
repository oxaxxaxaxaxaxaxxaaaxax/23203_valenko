#include <iostream>
#include <cstring>



typedef std::string Key;

typedef struct Value {
  unsigned age;
  unsigned weight;
}Value;

typedef struct Node{
  Key key;
  Value * data;
  bool flag;
  Node * next;
  Node(){
    data->age=0;
    data->weight=0;
    key ="";
    flag=0;
  }
  Node(Node& b){
    key = b.key;
    data->age=b.data->age;
    data->weight= b.data->weight;
    flag =b.flag;
  }
}Node;

class HashTable
{
  //Key name;
  //Value date;
  int curr_size =0;
  static constexpr int initial_capacity = 4;
  int capacity;
  Node ** table;

  void Rehash(HashTable& a,Node ** new_table){
    for(int i=0;i<capacity;i++){
      if(new_table[i]->key == "") continue;
      int index = hashFunction(*this,new_table[i]->key);
      table = new Node*[capacity];
      table[index] = new Node(*(new_table[i]));
    }
    delete [] new_table;
  }

  void expandMemoryIfNeeded(){
    curr_size++;
    if (curr_size >= capacity){
      int new_capacity = capacity*2;
      Node ** new_table = new Node*[new_capacity];
      new_table = static_cast <Node**>(std::memcpy (new_table, table, capacity*sizeof(Node*)));
      delete [] table;
      capacity = new_capacity;
      //table = new_table;
      Rehash(*this,new_table);
    }
  }

public:
  HashTable(){
    capacity = initial_capacity;
    table = new Node*[initial_capacity];
    for(int i=0; i< capacity;i++){
      table[i] = new Node;
    }
  }

  HashTable(int size){
    capacity = size;
    size = size;
    table = new Node*[capacity];
    for(int i=0; i< capacity;i++){
      table[i] = new Node;
    }
  }
  ~HashTable(){
    for(int i=0; i< capacity;i++){
      delete table[i];
    }
    delete [] table;
  }
  
  HashTable(const HashTable& b){
    curr_size = b.curr_size;
    capacity = b.capacity;
    table = new Node*[b.capacity];
    for (int i=0;i<b.capacity;i++){
      table[i] = b.table[i];
    }
  }

  HashTable(HashTable&& b){
    curr_size = b.curr_size;
    capacity = b.capacity;
    //table = new Node*[b.capacity];
    table = b.table;
    //for (int i=0;i<b.capacity;i++){
      //table[i] = b.table[i];
    //}
    //b.curr_size=0;
    //b.capacity =0;
    //delete [] b.table; //нужно ли это чистить если деструктор сам все почистит(не надо скорее всего)
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
    curr_size = b.curr_size;
    capacity = b.capacity;
    Node ** new_table = new Node*[b.capacity];
    for (int i=0;i<b.capacity;i++){
      new_table[i] = b.table[i];
    }
    delete [] table;
    table = new_table;
    return *this;
  }
  HashTable& operator=(HashTable&& b){
    curr_size = b.curr_size;
    capacity = b.capacity;
    Node ** new_table = new Node*[b.capacity];
    for (int i=0;i<b.capacity;i++){
      new_table[i] = b.table[i];
    }
    b.curr_size=0;
    b.capacity =0;
    delete [] b.table;
    delete [] table;
    table = new_table;
    return *this;
  }


  // Очищает контейнер.
  void clear(){
    for(int i =0; i< capacity;i++){
      if(table[i]->flag == 0){
        continue;
      }
      while(table[i]->next!= nullptr){
        table[i]->data->age=0;
        table[i]->data->weight=0;
        table[i]->key ="";
        table[i]=table[i]->next;
      }
      table[i]->data->age=0;
      table[i]->data->weight=0;
      table[i]->key ="";
    }
  }

  // Удаляет элемент по заданному ключу.
  bool erase(const Key& k){
    size_t index = hashFunction(*this, k);
    if(table[index]->flag==0){ return 0;}
    while(table[index]->key != k){
      table[index] = table[index]->next;
    }
    (table[index])->data->age = 0;
    (table[index])->data->age = 0;
    table[index]->key = "";
    return 1;
  }
  // Вставка в контейнер. Возвращаемое значение - успешность вставки.
  bool insert(const Key& k, const Value& v){
    expandMemoryIfNeeded();
    size_t index = hashFunction(*this, k);
    if(table[index]->flag == 0){
      (table[index])->data->age = v.age;
      (table[index])->data->weight = v.weight;
      (table[index])->key = k;
      table[index]->next = nullptr;
      table[index]->flag =1;
      return 1;
    }
    while((table[index])->next!= nullptr){
      table[index] = table[index]->next;
    }
    (table[index])->data->age = v.age;
    (table[index])->data->weight = v.weight;
    table[index]->flag=1;
    (table[index])->key = k;
    table[index]->next = nullptr;
    return 1;
  }

  // Проверка наличия значения по заданному ключу.
  bool contains(const Key& k) const{
    size_t index = hashFunction(*this, k);
    if(table[index]->flag ==0) return 0;
    while(table[index]->next != nullptr){
      if(table[index]->flag ==0) return 0;
    }
    return 1;
  }

  // Возвращает значение по ключу. Небезопасный метод.
  Value& operator[](const Key& k){
    size_t index = hashFunction(*this, k);
    if(table[index]->flag == 0){ }//&&&&7777??????
    while(table[index]->key != k){
      table[index] = (table[index])->next;
    }
    return *((table[index])->data);
  }

  // Возвращает значение по ключу. Бросает исключение при неудаче.
  Value& at(const Key& k){
    size_t index = hashFunction(*this, k);
    try{
      while(table[index]->key != k){
        if(table[index]->flag == 0){
          throw -1;
        }
        table[index] = (table[index])->next;
      }
    }
    catch(int a){
      std::cout<< "Value is not found" << std::endl;
    }
    return *((table[index])->data);
  }
  const Value& at(const Key& k) const{
    size_t index = hashFunction(*this, k);
    try{
      while(table[index]->key != k){
        if(table[index]->flag == 0){
          throw -1;
        }
        table[index] = (table[index])->next;
      }
    }
    catch(int a){
      std::cout<< "Value is not found" << std::endl;
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
    if(a.capacity != b.capacity) return 0;
    for(int i=0; i<a.capacity;i++){
      if(a.table[i]->flag != b.table[i]->flag) return 0;
      if((a.table[i]->flag == 0) && (b.table[i]->flag==0)) continue;
      while((a.table[i]-> next != nullptr) && (a.table[i]-> next != nullptr)){
        if((a.table[i]->data->age != b.table[i]->data->age)||(a.table[i]->data->weight!=b.table[i]->data->weight)||((a.table[i]->key.compare(b.table[i]->key)))) return 0;
        a.table[i] = a.table[i]->next;
        b.table[i] = b.table[i]->next;
      }
      if((a.table[i]->next == nullptr)&&(b.table[i]->next == nullptr)) continue;
      else return 0;
    }
    return 1;
  }
  friend bool operator!=(const HashTable& a, const HashTable& b){
    if(a.curr_size != b.curr_size) return 1;
    if(a.capacity != b.capacity) return 1;
    for(int i=0; i<a.capacity;i++){
      if(a.table[i]->flag != b.table[i]->flag) return 1;
      if((a.table[i]->flag == 0) && (b.table[i]->flag==0)) continue;
      while((a.table[i]-> next != nullptr) && (a.table[i]-> next != nullptr)){
        if((a.table[i]->data->age != b.table[i]->data->age)||(a.table[i]->data->weight!=b.table[i]->data->weight)||((a.table[i]->key.compare(b.table[i]->key)))) return 1;
        a.table[i] = a.table[i]->next;
        b.table[i] = b.table[i]->next;
      }
      if((a.table[i]->next == nullptr)&&(b.table[i]->next == nullptr)) continue;
      else return 1;
    }
    return 0;
  }

  size_t hashFunction(HashTable &a, const Key key) {
    size_t bucketIndex;
    size_t sum = 0;
    size_t factor = 31;
    for (size_t i = 0; i < key.length(); i++) {
        // sum = sum + (ascii value of
        // char * (primeNumber ^ x))...
        // where x = 1, 2, 3....n
        sum = ((sum % a.capacity) + (((int)key[i]) * factor) % a.capacity) % a.capacity;
        // factor = factor * prime
        // number....(prime
        // number) ^ x
        factor = ((factor % __INT16_MAX__) * (31 % __INT16_MAX__)) % __INT16_MAX__;
    }
    bucketIndex = sum;
    return bucketIndex;
  }
};




int main(void){
  HashTable a;
  Key k1 = "FIT";
  Value v1;
  v1.age =19;
  v1.weight =58;
  bool res1 = a.insert(k1, v1);
  bool res2 = a.contains("FIT");
}