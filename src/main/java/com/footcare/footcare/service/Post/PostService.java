package com.footcare.footcare.service.Post;

import com.footcare.footcare.Repository.Member.MemberRepository;
import com.footcare.footcare.Repository.Post.PostMemberRepository;
import com.footcare.footcare.Repository.Post.PostRepository;

import com.footcare.footcare.dto.Post.PostDTO;
import com.footcare.footcare.dto.Post.PostMemberDTO;
import com.footcare.footcare.entity.Member.Member;
import com.footcare.footcare.entity.Post.Post;
import com.footcare.footcare.entity.Post.PostMember;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMemberRepository postMemberRepository;
    @Autowired
    private MemberRepository memberRepository;


    // Entity -> DTO 변환
    private PostDTO convertToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setPostId(post.getPostId());
        dto.setCategoryId(post.getCategory_id());
        dto.setPostName(post.getPostName());
        dto.setPostContentName(post.getPostContentName());
        dto.setPostDate(post.getPostDate());
        dto.setPostView(post.getPostView());
        dto.setLikeCount(post.getLikeCount());
        //dto.setMemberId(post.getPostMembers().get(0).getMember().getMemberId());
        return dto;
    }

    // DTO -> Entity 변환
    private Post convertToEntity(PostDTO dto) {
        Post post = new Post();
        post.setPostId(dto.getPostId());
        post.setCategory_id(dto.getCategoryId());
        post.setPostName(dto.getPostName());
        post.setPostContentName(dto.getPostContentName());
        post.setPostDate(dto.getPostDate());
        post.setPostView(dto.getPostView());
        return post;
    }

    public PostMemberDTO convertToDto(PostMember postMember) {
        PostMemberDTO dto = new PostMemberDTO();
        dto.setPostMemberId(postMember.getPostMemberId());
        dto.setMemberId(postMember.getMember().getMemberId());
        dto.setPostId(postMember.getPost().getPostId());
        dto.setIsActive(postMember.getIsActive());
        return dto;
    }


    public PostMember convertDTOToPostMember(PostMemberDTO dto) {
        PostMember postMember = new PostMember();
        postMember.setPostMemberId(dto.getPostMemberId());
        postMember.setIsActive(dto.getIsActive());

        // Member와 Post는 ID로 조회하여 설정
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + dto.getMemberId()));
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + dto.getPostId()));

        postMember.setMember(member);
        postMember.setPost(post);
        return postMember;
    }

    public Map<String, Object> createPost(PostDTO postDTO) {
        Map<String, Object> response = new HashMap<>();

        // 사용자를 확인
        Member member = memberRepository.findById(postDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + postDTO.getMemberId()));


        // 오늘 작성된 게시물 개수 확인 (PostMember 기준)
        long todayActivePostCount = postMemberRepository.countByMemberAndCreatedDate(member, LocalDate.now());
        if (todayActivePostCount >= 10) {
            // 초과된 게시물의 상태를 'N'으로 변경
            List<PostMember> excessPosts = postMemberRepository.findExcessPostsByMemberAndCreatedDate(member, LocalDate.now());
            excessPosts.forEach(postMember -> postMember.setIsActive("N"));
            postMemberRepository.saveAll(excessPosts);


            response.put("status", "EXCEEDED_LIMIT");
            response.put("message", "하루에 작성할 수 있는 게시물은 최대 10개입니다.");
            response.put("isActive", "N"); // Add 'isActive' as 'N'

            response.put("excessPosts", excessPosts.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList()));
            return response;
        }


        // 게시물 생성 (DTO -> Entity 변환)
        Post post = convertToEntity(postDTO);
        Post savedPost = postRepository.save(post);

        if ("admin".equalsIgnoreCase(member.getId())) {
            post.setPostFg("admin");
            response.put("PostFg", "admin");
        } else {
            post.setPostFg("user");
            response.put("PostFg", "user");
        }
        // PostMember 생성 및 저장
        PostMember postMember = new PostMember();
        postMember.setPost(savedPost);
        postMember.setMember(member);
        postMember.setCreatedDate(LocalDate.now());
        postMember.setIsActive("Y"); // Set 'isActive' as 'Y'
        postMemberRepository.save(postMember);

        // 응답 구성 (Entity -> DTO 변환)
        response.put("status", "SUCCESS");
        response.put("message", "게시물이 성공적으로 생성되었습니다.");
        response.put("post", convertToDTO(savedPost));
        response.put("isActive", "Y"); // Add 'isActive' as 'Y'
        return response;
    }


    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PostDTO> getPostById(Long id) {
        return postRepository.findById(id)
                .map(this::convertToDTO);
    }

    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setPostName(postDTO.getPostName());
            post.setPostContentName(postDTO.getPostContentName());
            post.setPostDate(postDTO.getPostDate());
            post.setLikeCount(postDTO.getLikeCount());
            Post updatedPost = postRepository.save(post);
            return convertToDTO(updatedPost);
        } else {
            return null;
        }
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    // 게시물 조회 시 조회수 증가
    @Transactional
    public Optional<PostDTO> getPostByIdAndIncreaseView(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setPostView(post.getPostView() + 1);
            postRepository.save(post);
            PostDTO postDTO = convertToDTO(post);
            postDTO.setMemberId(post.getPostMembers().get(0).getMember().getMemberId());
            return Optional.of(postDTO);
        }
        return Optional.empty();
    }

    // 카테고리 ID로 게시물 조회
    public List<PostDTO> getPostsByCategoryId(Long categoryId) {
        List<Post> posts = postRepository.findByCategoryCategoryId(categoryId);
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PostDTO likePost(Long memberId, Long postId) {

        Optional<PostMember> existingLike = postMemberRepository.findByMemberIdAndPostId(memberId, postId);

        if (existingLike.isPresent()) {
            PostMember postMember = existingLike.get();
            if ("Y".equalsIgnoreCase(postMember.getLikefg())) {
                throw new IllegalStateException("Post is already liked by the user.");
            }

            postMember.setLikefg("Y");
            postMemberRepository.save(postMember);
        } else {

            PostMember postMember = new PostMember();

            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            postMember.setMember(member);

            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found"));
            postMember.setPost(post);

            postMember.setLikefg("Y");
            postMemberRepository.save(postMember);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setLikeCount((post.getLikeCount() != null ? post.getLikeCount() : 0L) + 1);
        Post updatedPost = postRepository.save(post);

        return convertToDTO(updatedPost);
    }

    public PostDTO unlikePost(Long memberId, Long postId) {

        Optional<PostMember> existingLike = postMemberRepository.findByMemberIdAndPostId(memberId, postId);

        if (existingLike.isEmpty() || !"Y".equalsIgnoreCase(existingLike.get().getLikefg())) {
            throw new IllegalStateException("Post is not liked by the user.");
        }

        PostMember postMember = existingLike.get();
        postMember.setLikefg("N");
        postMemberRepository.save(postMember);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Long currentLikeCount = (post.getLikeCount() != null ? post.getLikeCount() : 0L);
        if (currentLikeCount > 0) {
            post.setLikeCount(currentLikeCount - 1);
        }
        Post updatedPost = postRepository.save(post);

        return convertToDTO(updatedPost);
    }


    public List<PostDTO> getPostsLikedByMember(Long memberId) {
        List<Post> likedPosts = postMemberRepository.findPostsLikedByMember(memberId);

        return likedPosts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PostDTO> getTop5PostsByLikeCount() {
        List<Post> topPosts = postRepository.findTop5ByOrderByLikeCountDesc();
        return topPosts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public String isPostLikedByUser(Long memberId, Long postId) {
        Optional<String> likeStatus = postMemberRepository.findLikeStatusByMemberIdAndPostId(memberId, postId);
        return likeStatus.orElse("N");
    }
}
