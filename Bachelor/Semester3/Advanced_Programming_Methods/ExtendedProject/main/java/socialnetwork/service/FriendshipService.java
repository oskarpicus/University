package socialnetwork.service;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.User;
import socialnetwork.repository.Repository;
import socialnetwork.repository.paging.Page;
import socialnetwork.repository.paging.Pageable;
import socialnetwork.repository.paging.PageableImplementation;
import socialnetwork.repository.paging.PagingRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FriendshipService implements PagingService<Tuple<Long,Long>,Friendship> {

    private final Community community;
    private final PagingRepository<Tuple<Long,Long>, Friendship> repo;
    private final PagingRepository<Long, User> userRepository;

    public FriendshipService(PagingRepository<Tuple<Long, Long>, Friendship> repo, PagingRepository<Long, User> userRepository) {
        community = new Community(userRepository);
        this.repo = repo;
        this.userRepository = userRepository;
    }


    /**
     * Method for adding a new friendship
     * @param friendship : Friendship, user to be added
     * @return an {@code Optional} - null if the user was saved,
     *                             - the user (id already exists)
     */
    public Optional<Friendship> add(Friendship friendship) {
        friendship.setDate();
        // we check if the ids of the friendship refer to users
        Tuple<Long,Long> ids = friendship.getId();
        Optional<User> user1 = this.userRepository.findOne(ids.getLeft());
        if(user1.isEmpty())
            throw new ServiceException("Id "+ids.getLeft()+" does not refer a user");
        Optional<User> user2 = this.userRepository.findOne(ids.getRight());
        if(user2.isEmpty())
            throw new ServiceException("Id "+ids.getRight()+" does not refer a user");
        return repo.save(friendship);
    }

    /**
     *  removes the friendship with the specified id
     * @param ids : id of the friendship to be deleted
     * @return an {@code Optional}
     *            - null if there is no entity with the given id,
     *            - the removed entity, otherwise
     */
    @Override
    public Optional<Friendship> remove(Tuple<Long, Long> ids) {
        return repo.delete(ids);
    }

    @Override
    public Optional<Friendship> findOne(Tuple<Long, Long> longLongTuple) {
        return this.repo.findOne(longLongTuple);
    }

    /**
     * Method for obtaining all the saved friendships
     * @return : list : List<Friendship>, which stores all the saved users
     */
    public List<Friendship> findAll(){
        Iterable<Friendship> all = repo.findAll();
        return StreamSupport.stream(all.spliterator(),false).collect(Collectors.toList());
    }

    /**
     * Method for obtaining the number of communities in the social network
     * @return nr : int, represents the total number of communities
     */
    public int getNumberOfCommunities(){
        return this.community.getNumberOfCommunities();
    }

    /**
     * Method for obtaining the most sociable community
     * ( the connected component with the longest path )
     * @return list : List<User>, contains all of the users in the most sociable community
     */
    public List<User> getMostSociable(){
        return community.getMostSociableCommunity();
    }

    @Override
    public List<Friendship> getEntities(int page){
        Pageable pageable = new PageableImplementation(page,pageSize);
        Page<Friendship> all = repo.findAll(pageable);
        return all.getContent().collect(Collectors.toList());
    }

    public List<Friendship> getFriendshipsPage(int pageNumber, User user){
        Long id = user.getId();
        return this.findAll().stream()
                .filter(friendship -> friendship.getId().getRight().equals(id) ||
                        friendship.getId().getLeft().equals(id))
                .skip(pageNumber  * PagingService.pageSize)
                .limit(PagingService.pageSize)
                .collect(Collectors.toList());
    }
}
