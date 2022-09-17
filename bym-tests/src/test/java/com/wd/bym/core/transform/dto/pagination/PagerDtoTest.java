package com.wd.bym.core.transform.dto.pagination;

import com.wd.bym.context.AbstractMockitoTest;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Test les cas :
 *  - aucuns champs spécifiés (builder et constructeur)
 *  - sortOrder non spécifié
 *  - tous les champs spécifiés (builder et constructeur)
 *  - toPageRequest dans un cas normal
 *  - toPageRequest dans le cas où pageSize < 1
 */
class PagerDtoTest extends AbstractMockitoTest {

    public static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(PagerDto.builder().build(),
                        0, 0, null, Sort.DEFAULT_DIRECTION), // expectations
                Arguments.of(new PagerDto(),
                        0, 0, null, Sort.DEFAULT_DIRECTION), // expectations
                Arguments.of(PagerDto.builder()
                                .page(1)
                                .pageSize(3)
                                .sortField("sortField")
                                .sortOrder(Sort.Direction.DESC)
                                .build(),
                        1, 3, "sortField", Sort.Direction.DESC), // expectations
                Arguments.of(new PagerDto(1, 3, "sortField", Sort.Direction.DESC),
                        1, 3, "sortField", Sort.Direction.DESC), // expectations
                Arguments.of(new PagerDto(-1, -3, "sortField", Sort.Direction.DESC),
                        -1, -3, "sortField", Sort.Direction.DESC) // expectations
        );
    }

    @DisplayName("Testing with different parameters")
    @ParameterizedTest(name = "#{index} - PagerDto should have page = {1}, pagerSize = {2}, sortField = \"{3}\" and sortOrder = \"{4}\"")
    @MethodSource("provideArguments")
    void whenHaveNoParams_shouldHavePageAndPageSizeEqualsToZero(PagerDto pagerDto,
                                                                int expectedPage,
                                                                int expectedPageSize,
                                                                String expectedSortField,
                                                                Sort.Direction expectedSortOrder) {
        // ASSERT PAGE
        assertThat(pagerDto.getPage()).isEqualTo(expectedPage);

        // ASSERT PAGE SIZE
        assertThat(pagerDto.getPageSize()).isEqualTo(expectedPageSize);

        // ASSERT SORT FIELD
        AbstractStringAssert<?> assertGetSortField = assertThat(pagerDto.getSortField());
        if(expectedSortField == null) assertGetSortField.isNull();
        else assertGetSortField.isEqualTo(expectedSortField);

        // ASSERT SORT ORDER
        ObjectAssert<?> assertGetSortOrder = assertThat(pagerDto.getSortOrder());
        if(expectedSortOrder == null) assertGetSortOrder.isNull();
        else assertGetSortOrder.isEqualTo(expectedSortOrder);
    }

    @Test
    void whenUsingToPageRequest_ifSortFieldIsNullOrEmpty_shouldReturnPageRequestUnsorted() {
        var pagerDto = PagerDto.builder().pageSize(1).build();

        assertThat(pagerDto.toPageRequest().getPageNumber()).isEqualTo(0);
        assertThat(pagerDto.toPageRequest().getPageSize()).isEqualTo(1);
        assertThat(pagerDto.toPageRequest().getSort()).isEqualTo(Sort.unsorted());
    }

    @Test
    void whenUsingToPageRequest_ifPagerDtoPageSizeIsEqualToZero_shouldThrowIllegalArgumentException() {
        var pagerDto = PagerDto.builder().build();
        Assertions.assertThrows(IllegalArgumentException.class, pagerDto::toPageRequest);
    }

    @Test
    void whenUsingToPageRequest_ifPagerDtoPageSizeIsLessThanOne_shouldThrowIllegalArgumentException() {
        var pagerDto = PagerDto.builder().pageSize(-1).build();
        Assertions.assertThrows(IllegalArgumentException.class, pagerDto::toPageRequest);
    }
}