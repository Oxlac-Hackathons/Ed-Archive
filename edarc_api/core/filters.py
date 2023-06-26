import django_filters
from django_filters import rest_framework as filters
from datetime import datetime
from . import models


class ResourceFilter(filters.FilterSet):
    class Meta:
        model = models.Resource
        fields = ["difficulty"]
