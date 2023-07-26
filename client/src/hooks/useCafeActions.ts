import { InfiniteData, useMutation, useQueryClient } from '@tanstack/react-query';
import client from '../client';
import { Cafe } from '../types';

type SetLikedCafeParams = {
  cafeId: Cafe['id'];
  isLiked: boolean;
};

const withIsLiked = (cafes: InfiniteData<{ cafes: Cafe[] }>, cafeId: Cafe['id'], isLiked: Cafe['isLiked']) => {
  return {
    ...cafes,
    pages: cafes.pages.map((page) => ({
      ...page,
      cafes: page.cafes.map((cafe) => {
        if (cafe.id !== cafeId) return cafe;

        return {
          ...cafe,
          isLiked,
          likeCount: cafe.likeCount + (cafe.isLiked === isLiked ? 0 : isLiked ? 1 : -1),
        };
      }),
    })),
  };
};

const useCafeActions = () => {
  const queryClient = useQueryClient();
  const { mutate: setLikedCafe } = useMutation({
    mutationFn: ({ cafeId, isLiked }: SetLikedCafeParams) => client.setLikedCafe(cafeId, isLiked),
    onMutate: async ({ cafeId, isLiked }) => {
      await queryClient.cancelQueries(['cafes', cafeId, 'isLiked']);

      const cafes: InfiniteData<{ cafes: Cafe[] }> | undefined = queryClient.getQueryData(['cafes']);

      queryClient.setQueryData(['cafes'], cafes ? withIsLiked(cafes, cafeId, isLiked) : cafes);
      return { previous: cafes };
    },
    onError: (_, __, context) => {
      queryClient.setQueryData(['cafes'], context?.previous);
    },
  });

  return { setLikedCafe };
};

export default useCafeActions;
