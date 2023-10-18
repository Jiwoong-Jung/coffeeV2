package project.coffee.controller.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.coffee.domain.dto.upload.CoffeeImageDTO;
import project.coffee.domain.dto.upload.CoffeeDTO;
import project.coffee.domain.entity.CoffeeImage;
import project.coffee.domain.entity.CoffeeList;
import project.coffee.repository.CoffeeImage.CoffeeImageRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class UploadTestController {

    @Autowired
    CoffeeImageRepository coffeeImageRepository;


    @GetMapping("/uploadEx")
    public void uploadEx() {

    }

    @GetMapping("/coffeeRegist")
    public String CoffeeRegist() {
        return "coffeeRegister";
    }

    @PostMapping("/register")
    public String register(CoffeeDTO coffeeDTO, RedirectAttributes redirectAttributes){
        log.info("coffeeDTO: " + coffeeDTO);

        // 추후 service로 만들어야 함
        Map<String, Object> entityMap = dtoToEntity(coffeeDTO);
        CoffeeList movie = (CoffeeList) entityMap.get("coffee");
        List<CoffeeImage> coffeeImageList = (List<CoffeeImage>) entityMap.get("imgList");

        log.info("coffeeImageList: " + coffeeImageList);
        coffeeImageList.forEach(movieImage -> {
            coffeeImageRepository.save(movieImage);
        });
        //끝

        return "redirect:/";
    }

    private Map<String, Object> dtoToEntity(CoffeeDTO movieDTO){

        Map<String, Object> entityMap = new HashMap<>();

        CoffeeList movie = CoffeeList.builder()
                .title(movieDTO.getTitle())
                .build();

        entityMap.put("coffee", movie);

        List<CoffeeImageDTO> imageDTOList = movieDTO.getImageDTOList();

        if(imageDTOList != null && imageDTOList.size() > 0 ) { //MovieImageDTO 처리

            List<CoffeeImage> movieImageList = imageDTOList.stream().map(movieImageDTO ->{

                CoffeeImage movieImage = CoffeeImage.builder()
                        .path(movieImageDTO.getPath())
                        .imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid())
                        .build();
                return movieImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList", movieImageList);
        }

        return entityMap;
    }
}
