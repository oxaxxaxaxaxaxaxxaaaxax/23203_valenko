
#include <cstring>
#include <iostream>



typedef std::string Key;
static constexpr int initial_capacity = 4;

struct Value {
  unsigned int age =0;
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
  bool flag =0;
  Node * next = nullptr;
  Node(){
    data = new Value;
    //next = new Node;
    //next = nullptr;
    //data->age=0;
    //data->weight=0;
  }
  Node(const Key &k, const Value& v):key(k),flag(1){
    data = new Value;
    data->age = v.age;
    data->weight = v.weight;
  }
  Node(Node& b):key(b.key),flag (b.flag){
    data->age=b.data->age;
    data->weight=b.data->weight;
  }
  Node& operator=(const Node& b){
    key=b.key;
    flag =b.flag;
    data->age=b.data->age;
    data->weight=b.data->weight;
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
    for(int i=0; i< capacity;i++){
      delete table[i];
    }
    delete [] table;
  }

  HashTable(int size):capacity(size),curr_size(size){
    table = new Node*[capacity];
    for(int i=0; i< capacity;i++){
      table[i] = new Node;
    }
  }

  HashTable(const HashTable& b):curr_size(b.curr_size),capacity(b.capacity){
    table = new Node*[b.capacity];
    for (int i=0;i<b.capacity;i++){
      table[i] = b.table[i];
    }
  }

  HashTable(HashTable&& b){
    //table = new Node*[b.capacity];
    //table = b.table;
    for (int i=0;i<capacity;i++){
      delete [] table[i]; 
    }
    delete [] table;
    curr_size = b.curr_size;
    capacity = b.capacity;
    table = new Node*[capacity];
    for (int i=0;i<capacity;i++){
      table[i] = b.table[i];
    }
    for (int i=0;i<b.capacity;i++){
      while(b.table[i]->next!=nullptr){
        b.table[i]->data = nullptr;
        b.table[i]=b.table[i]->next;
      }
      b.table[i]->data = nullptr;
      b.table[i] = nullptr;
    }
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
    if(this == &b) return *this;
    curr_size = b.curr_size;
    capacity = b.capacity;
    Node ** new_table = new Node*[b.capacity];
    for (int i=0;i<b.capacity;i++){
      new_table[i] = b.table[i];
    }
    //b.curr_size=0;
    //b.capacity =0;
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
        *(table[i]) = Node();
        //table[i]->data->age=0;
        //table[i]->data->weight=0;
        //table[i]->key ="";
        table[i]=table[i]->next;
      }
      *(table[i]) = Node();
      //table[i]->data->age=0;
      //table[i]->data->weight=0;
      //table[i]->key ="";
    }
  }

  // Удаляет элемент по заданному ключу.
  bool erase(const Key& k){
    size_t index = hashFunction(k);
    while(table[index]->key != k){
      table[index] = table[index]->next;
      if(table[index] == nullptr) return 0;
    }
    (table[index])->data->age = 0;
    (table[index])->data->age = 0;
    table[index]->key = "";
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
      *(table[index]) = Node(k, v);
      //table[index]->next = new Node;
      return 1;
    }
    if(table[index]->key == k) return 0;
    while((table[index])->next!= nullptr){
      if(table[index]->next->key == k) return 0;
      table[index] = table[index]->next;
    }
    *(table[index]) = Node(k, v);
    table[index]->next = new Node;
    return 1;
  }

  // Проверка наличия значения по заданному ключу.
  bool contains(const Key& k) const{ 
    size_t index = hashFunction(k);
    while(table[index]->next != nullptr){
      if(k == table[index]->key) return 1;
      table[index] = table[index]->next;
    }
    if(k == table[index]->key) return 1;
    return 0;
  }

  // Возвращает значение по ключу. Небезопасный метод.
  Value& operator[](const Key& k)const{
    size_t index = hashFunction(k);
    if(!contains(k)){ 
      table[index] = new Node;
      table[index]->key = k;
      return *((table[index])->data);
    }
    while(table[index]->key != k){
      table[index] = (table[index])->next;
    }
    return *((table[index])->data);
  }

  // Возвращает значение по ключу. Бросает исключение при неудаче.
  Value& at(const Key& k){
    size_t index = hashFunction(k);
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
    size_t index = hashFunction(k);
    try{
      while(table[index]->key != k){
        if(table[index]->flag == 0){
          throw std::runtime_error("Value is not found");
        }
        table[index] = (table[index])->next;
      }
    }
    catch(const std::runtime_error&e){
      std::cout<< e.what()<< std::endl;
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
      if(b.operator[](b.table[i]->key) != a.operator[](b.table[i]->key)) return 0;
    }
    return 1;
  }
  friend bool operator!=(const HashTable& a, const HashTable& b){
    if(a.curr_size != b.curr_size) return 1;
    for(int i=0; i<a.capacity;i++){
      if(b.operator[](b.table[i]->key) != a.operator[](b.table[i]->key)) return 1;
    }
    return 0;
  }
  private:
    int curr_size =0;
    int capacity;
    Node ** table;


  void Rehash(Node ** new_table){
    Node ** tmp_table = new Node*[capacity];
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
    delete [] new_table;
  }

  void expandMemoryIfNeeded(){
    int new_capacity = capacity*2;
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
    Rehash(new_table);
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




int main(void){
  HashTable a;
  Value v1 = {19, 58};
  Key k1 = "Oksana";
  Value v2 = {22, 67};
  Key k2 = "Olesya";
  Value v3 = {29, 51};
  Key k3 = "Anya";
  int res = a.insert(k1,v1);
  res+= a.insert(k2,v2);
  res+= a.insert(k3,v3);
  HashTable b;
  b=a;
  std::cout << "fhhffh" <<std::endl;
  return 0;
}