abstract class Repository<ID, E> {
  Future save(E e);

  Future delete(ID id);

  Future update(E e);

  Future find(ID id);

  Future<List<E>> findAll();
}