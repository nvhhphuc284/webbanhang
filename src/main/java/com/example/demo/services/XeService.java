package com.example.demo.services;
import com.example.demo.entity.Xe;
import com.example.demo.repositories.IXeRepository;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class XeService {
    private final IXeRepository xeRepository;
    public List<Xe> getAllXes(Integer pageNo,
                                  Integer pageSize,
                                  String sortBy) {
        return xeRepository.findAllXes(pageNo, pageSize, sortBy);
    }
    public Optional<Xe> getXeById(Long id) {
        return xeRepository.findById(id);
    }
    //add
    public void addXe(Xe xe) {
        xeRepository.save(xe);
    }
    //update
    public void updateXe(@NotNull Xe xe) {

        Xe existingXe = xeRepository.findById(xe.getId()).orElse(null);

        Objects.requireNonNull(existingXe).setTitle(xe.getTitle());
        existingXe.setAuthor(xe.getAuthor());
        existingXe.setPrice(xe.getPrice());
        existingXe.setCategory(xe.getCategory());
        xeRepository.save(existingXe);
    }
    public void deleteXeById(Long id) {
        xeRepository.deleteById(id);
    }
    public List<Xe> searchXe(String keyword) {
        return xeRepository.searchXe(keyword);
    }
}
