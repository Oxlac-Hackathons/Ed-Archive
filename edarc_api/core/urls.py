from django.urls import path
from .views import (
    ArchiveListAPIView,
    ArchiveDetailAPIView,
    ResourceListAPIView,
    ResourceDetailAPIView,
    ResourceListByArchiveAPIView,
    ResourceDetailByArchiveAPIView,
)

urlpatterns = [
    path("archives/", ArchiveListAPIView.as_view(), name="archive-list"),
    path("archives/<int:pk>/", ArchiveDetailAPIView.as_view(), name="archive-detail"),
    path(
        "archives/<int:archive_id>/resources/",
        ResourceListByArchiveAPIView.as_view(),
        name="resource-list-by-archive",
    ),
    path(
        "archives/<int:archive_id>/resources/<int:pk>",
        ResourceDetailByArchiveAPIView.as_view(),
        name="resource-detail-by-archive",
    ),
    path("resources/", ResourceListAPIView.as_view(), name="resource-list"),
    path(
        "resources/<int:pk>/", ResourceDetailAPIView.as_view(), name="resource-detail"
    ),
]
