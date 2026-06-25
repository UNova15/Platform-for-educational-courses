package org.platform.platformforeducationalcourses.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.creator.factory.TestFactory;
import org.platform.platformforeducationalcourses.dto.test.TestCreateDto;
import org.platform.platformforeducationalcourses.dto.test.TestCreateResponse;
import org.platform.platformforeducationalcourses.dto.test.TestFindResponse;
import org.platform.platformforeducationalcourses.dto.test.TestUpdateDto;
import org.platform.platformforeducationalcourses.mapper.TestMapper;
import org.platform.platformforeducationalcourses.repository.course.TestRepository;
import org.platform.platformforeducationalcourses.service.domain.TestService;

@ExtendWith(MockitoExtension.class)
class TestServiceTest {

    @Mock
    private TestRepository testRepository;

    @Mock
    private TestMapper testMapper;

    @Mock
    private TestFactory testFactory;

    @InjectMocks
    private TestService testService;

    @Test
    void createTest_Success() {
        TestCreateDto dto = mock(TestCreateDto.class);
        org.platform.platformforeducationalcourses.domain.course.Test mockTest =
                mock(org.platform.platformforeducationalcourses.domain.course.Test.class);

        when(testFactory.createTestFromCreateDto(dto, 1L)).thenReturn(mockTest);
        when(testRepository.save(mockTest)).thenReturn(mockTest);

        TestCreateResponse expectedResponse = mock(TestCreateResponse.class);
        when(testMapper.toTestCreateResponse(mockTest)).thenReturn(expectedResponse);

        TestCreateResponse actualResponse = testService.createTest(dto, 1L);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void deleteTest_Success() {
        org.platform.platformforeducationalcourses.domain.course.Test mockTest =
                mock(org.platform.platformforeducationalcourses.domain.course.Test.class);
        when(testRepository.findById(1L)).thenReturn(Optional.of(mockTest));

        testService.deleteTest(1L);

        verify(testRepository).delete(mockTest);
    }

    @Test
    void updateTest_Success() {
        org.platform.platformforeducationalcourses.domain.course.Test mockTest =
                mock(org.platform.platformforeducationalcourses.domain.course.Test.class);
        when(mockTest.getId()).thenReturn(100L);
        when(testRepository.findById(1L)).thenReturn(Optional.of(mockTest));

        TestUpdateDto dto = mock(TestUpdateDto.class);
        when(dto.testId()).thenReturn(1L);
        when(dto.description()).thenReturn("Desc");
        when(dto.orderIndex()).thenReturn(1);
        when(dto.questions()).thenReturn(List.of());

        when(testFactory.createQuestionsFromUpdateDto(any(), eq(100L))).thenReturn(Set.of());

        testService.updateTest(dto);

        verify(mockTest).update(eq("Desc"), eq(1), any());
        verify(testRepository).save(mockTest);
    }

    @Test
    void getTest_Success() {
        org.platform.platformforeducationalcourses.domain.course.Test mockTest =
                mock(org.platform.platformforeducationalcourses.domain.course.Test.class);
        when(testRepository.findById(1L)).thenReturn(Optional.of(mockTest));

        TestFindResponse expectedResponse = mock(TestFindResponse.class);
        when(testMapper.toTestFindResponse(mockTest)).thenReturn(expectedResponse);

        TestFindResponse actualResponse = testService.getTest(1L);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void generic_ThrowsException_IfNotFound() {
        when(testRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> testService.deleteTest(1L));
        assertThrows(NoSuchElementException.class, () -> testService.getTest(1L));

        TestUpdateDto dto = mock(TestUpdateDto.class);
        when(dto.testId()).thenReturn(1L);
        assertThrows(NoSuchElementException.class, () -> testService.updateTest(dto));
    }
}
