package com.sparta.springcore.service;

import com.sparta.springcore.model.Folder;
import com.sparta.springcore.model.Product;
import com.sparta.springcore.model.User;
import com.sparta.springcore.repository.FolderRepository;
import com.sparta.springcore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public FolderService(FolderRepository folderRepository, ProductRepository productRepository) {
        this.folderRepository = folderRepository;
        this.productRepository = productRepository;
    }
    public List<Folder> addFolders(List<String> foldernames, User user) {
        List<Folder> folderList = new ArrayList<>();

        for(String folderName : foldernames){
            Folder folder = new Folder(folderName, user);
            folderList.add(folder);
        }

        return folderRepository.saveAll(folderList);
    }

    public List<Folder> getFolders(User user) {
        return folderRepository.findAllByUser(user);
    }

    public Page<Product> getProductsInForder(Long folderId, int page, int size, String sortBy, boolean isAsc, User user) {
        Long userId = user.getId();
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAllByUserIdAndFolderList_Id(userId, folderId, pageable);
    }
}
