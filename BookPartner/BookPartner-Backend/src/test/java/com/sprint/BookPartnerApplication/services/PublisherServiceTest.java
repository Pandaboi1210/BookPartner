package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.PublishersRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.PublishersResponseDTO;
import com.sprint.BookPartnerApplication.entity.Employee;
import com.sprint.BookPartnerApplication.entity.Publishers;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.repository.PublishersRepository;
import com.sprint.BookPartnerApplication.servicesImpl.PublisherServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PublisherServiceTest {

    @Mock
    private PublishersRepository publishersRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    private PublishersRequestDTO testPublisherRequestDTO;
    private Publishers testPublisher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testPublisherRequestDTO = new PublishersRequestDTO();
        testPublisherRequestDTO.setPubId("0877");
        testPublisherRequestDTO.setPubName("Tech Publishing House");
        testPublisherRequestDTO.setCity("San Francisco");
        testPublisherRequestDTO.setState("CA");
        testPublisherRequestDTO.setCountry("USA");

        testPublisher = new Publishers();
        testPublisher.setPubId("0877");
        testPublisher.setPubName("Tech Publishing House");
        testPublisher.setCity("San Francisco");
        testPublisher.setState("CA");
        testPublisher.setCountry("USA");
    }

    // ✅ 1. Create Publisher
    @Test
    public void testCreatePublisher() {
        when(publishersRepository.save(any(Publishers.class))).thenReturn(testPublisher);

        PublishersResponseDTO result = publisherService.createPublisher(testPublisherRequestDTO);

        assertNotNull(result);
        assertEquals("0877", result.getPubId());
        assertEquals("Tech Publishing House", result.getPubName());
        verify(publishersRepository, times(1)).save(any(Publishers.class));
    }

    // ❌ 2. Create Publisher - Null Input
    @Test
    public void testCreatePublisher_NullInput() {
        assertThrows(Exception.class, () -> {
            publisherService.createPublisher(null);
        });
    }

    // ✅ 3. Get Publisher By ID
    @Test
    public void testGetPublisherById() {
        when(publishersRepository.findById("0877"))
                .thenReturn(Optional.of(testPublisher));

        PublishersResponseDTO result = publisherService.getPublisherById("0877");

        assertNotNull(result);
        assertEquals("0877", result.getPubId());
        verify(publishersRepository, times(1)).findById("0877");
    }

    // ❌ 4. Get Publisher By ID - Not Found
    @Test
    public void testGetPublisherById_NotFound() {
        when(publishersRepository.findById("999"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            publisherService.getPublisherById("999");
        });
    }

    // ✅ 5. Get All Publishers
    @Test
    public void testGetAllPublishers() {
        List<Publishers> list = new ArrayList<>();
        list.add(testPublisher);

        when(publishersRepository.findAll()).thenReturn(list);

        List<PublishersResponseDTO> result = publisherService.getAllPublishers();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    // ⚠️ 6. Get All Publishers - Empty
    @Test
    public void testGetAllPublishers_Empty() {
        when(publishersRepository.findAll()).thenReturn(new ArrayList<>());

        List<PublishersResponseDTO> result = publisherService.getAllPublishers();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    // ✅ 7. Update Publisher
    @Test
    public void testUpdatePublisher() {
        when(publishersRepository.findById("0877"))
                .thenReturn(Optional.of(testPublisher));
        when(publishersRepository.save(any(Publishers.class)))
                .thenReturn(testPublisher);

        PublishersResponseDTO result =
                publisherService.updatePublisher("0877", testPublisherRequestDTO);

        assertNotNull(result);
        verify(publishersRepository, times(1)).save(any(Publishers.class));
    }

    // ❌ 8. Update Publisher - Not Found
    @Test
    public void testUpdatePublisher_NotFound() {
        when(publishersRepository.findById("999"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            publisherService.updatePublisher("999", testPublisherRequestDTO);
        });
    }

    // ❌ 9. Delete Publisher With Dependencies
    @Test
    public void testDeletePublisher_WithDependencies() {

        // Step 1: Employees list (correct type)
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());

        // Step 2: Titles list
        List<Title> titles = new ArrayList<>();
        titles.add(new Title());

        // Step 3: Mock behavior
        when(publishersRepository.findById("0877"))
                .thenReturn(Optional.of(testPublisher));

        when(publishersRepository.getEmployeesByPublisherId("0877"))
                .thenReturn(employees);

        when(publishersRepository.getTitlesByPublisherId("0877"))
                .thenReturn(titles);

        // Step 4: Expect exception
        assertThrows(RuntimeException.class, () -> {
            publisherService.deletePublisher("0877");
        });
    }
}