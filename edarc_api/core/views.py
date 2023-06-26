from django.shortcuts import render
from django_filters.rest_framework import DjangoFilterBackend
from rest_framework import generics
from rest_framework.viewsets import ModelViewSet, GenericViewSet
from rest_framework.permissions import DjangoModelPermissions
from rest_framework.mixins import ListModelMixin
from rest_framework.viewsets import ModelViewSet
from rest_framework.filters import SearchFilter, OrderingFilter
from .filters import ResourceFilter
from .models import Archive, Resource
from .serializers import ArchiveSerializer, ResourceSerializer


class ArchiveListAPIView(generics.ListCreateAPIView):
    """This endpoint allows you to create or retrieve a list of archives."""

    queryset = Archive.objects.all()
    serializer_class = ArchiveSerializer
    filter_backends = [SearchFilter]
    search_fields = ["name", "description"]


class ArchiveDetailAPIView(generics.RetrieveUpdateDestroyAPIView):
    """This endpoint allows you to retrieve, update, or delete an archive."""

    queryset = Archive.objects.all()
    serializer_class = ArchiveSerializer


class ResourceListAPIView(generics.ListCreateAPIView):
    """This endpoint allows you to create or retrieve a list of resources."""

    queryset = Resource.objects.all()
    serializer_class = ResourceSerializer
    filter_backends = [SearchFilter]
    search_fields = ["name", "description"]


class ResourceDetailAPIView(generics.RetrieveUpdateDestroyAPIView):
    """This endpoint allows you to retrieve, update, or delete a resource."""

    queryset = Resource.objects.all()
    serializer_class = ResourceSerializer


class ResourceListByArchiveAPIView(generics.ListCreateAPIView):
    """This endpoint allows you to list all resources in an archive."""

    serializer_class = ResourceSerializer
    filter_backends = [DjangoFilterBackend, SearchFilter]
    filterset_class = ResourceFilter
    search_fields = ["name", "description"]

    def get_queryset(self):
        """Return all resources in an archive."""
        archive_id = self.kwargs["archive_id"]
        return Resource.objects.filter(archive_id=archive_id)


class ResourceDetailByArchiveAPIView(generics.RetrieveUpdateDestroyAPIView):
    serializer_class = ResourceSerializer

    def get_queryset(self):
        archive_id = self.kwargs["archive_id"]
        return Resource.objects.filter(archive_id=archive_id)
